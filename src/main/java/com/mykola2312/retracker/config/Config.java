package com.mykola2312.retracker.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
	public static class TrackerConfig {
		public List<String> endpoints;
	}
	
	public TrackerConfig tracker;
	
	public static Config loadConfig(String configPath) throws IOException {
		return new ObjectMapper()
				.readerFor(Config.class)
					.readValue(new File(configPath));
	}
}
