package com.mykola2312.retracker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mykola2312.retracker.config.Config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		final Option configFileOpt = new Option("c", "config", true, "config.json path");
		final Option logDirOpt = new Option("l", "log", true, "log dir path");
		final Options options = new Options()
				.addOption(configFileOpt)
				.addOption(logDirOpt);
		
		CommandLine cli = null;
		try {
			cli = new DefaultParser().parse(options, args);
		} catch (ParseException e) {
			System.err.printf("Argument error: %s\n", e.toString());
			System.exit(1);
		}
		
		String configFilePath = cli.getOptionValue(configFileOpt);
		if (configFilePath == null) {
			System.err.println("config.json path not specified. exiting");
			System.exit(1);
		}
		
		// load config
		Config config = null;
		try {
			config = Config.loadConfig(configFilePath);
		} catch (IOException e) {
			System.err.printf("failed to read %s: %s\n", configFilePath, e.toString());
			System.exit(1);
		}
		
		String logDirPath = cli.getOptionValue(logDirOpt);
		if (logDirPath != null) {
			// setup file logging
			File logDir = new File(logDirPath);
			if (!logDir.exists()) {
				if (!logDir.mkdir()) {
					System.err.println("Failed to create log dir");
					System.exit(1);
				}
			}
			
			// https://stackoverflow.com/questions/47299109/programmatically-add-appender-in-logback	
			final LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
			final PatternLayoutEncoder ple = new PatternLayoutEncoder();
			ple.setPattern("%d{HH:mm:ss} %-5level %logger{0} - %msg%n");
			ple.setContext(lc);
			ple.start();
			
			FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
			fileAppender.setFile(Path.of(logDirPath, "log_out.log").toString());
			fileAppender.setEncoder(ple);
			fileAppender.setContext(lc);
			fileAppender.start();
			
			ch.qos.logback.classic.Logger logbackLogger =
	                (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("com.mykola2312");
			logbackLogger.addAppender(fileAppender);
			logbackLogger.setLevel(Level.INFO);
			logbackLogger.setAdditive(false);
		}
		
		log.info("retracker started!");
	}
}
