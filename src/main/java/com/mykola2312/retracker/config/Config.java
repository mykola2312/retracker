package com.mykola2312.retracker.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
	public static Config loadConfig(String configPath) throws IOException {
		return new ObjectMapper()
				.readerFor(Config.class)
					.readValue(new File(configPath));
	}
}
