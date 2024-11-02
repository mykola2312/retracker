package com.mykola2312.retracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mykola2312.retracker.config.Config;

/* Class responsible for managing running server threads
 * and repeating timer-based tasks
 */
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	private Config config;
	
	public Application(Config config) {
		this.config = config;
	}
}
