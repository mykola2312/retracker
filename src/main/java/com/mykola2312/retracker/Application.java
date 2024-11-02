package com.mykola2312.retracker;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mykola2312.retracker.config.Config;
import com.mykola2312.retracker.tracker.Tracker;
import com.mykola2312.retracker.tracker.TrackerEndpoint;
import com.mykola2312.retracker.tracker.TrackerHTTPServer;
import com.mykola2312.retracker.tracker.TrackerServer;

/* Class responsible for managing running server threads
 * and repeating timer-based tasks
 */
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	private Config config;
	
	private Tracker tracker;
	private LinkedList<TrackerServer> servers;
	
	public Application(Config config) {
		this.config = config;
	}
	
	private class TrackerServerFactory {
		private Tracker tracker;
		private String endpointUrl;
		
		public TrackerServerFactory(Tracker tracker, String endpointUrl) {
			this.tracker = tracker;
			this.endpointUrl = endpointUrl;
		}
		
		public TrackerServer create() throws Exception {
			TrackerEndpoint endpoint = TrackerEndpoint.parseEndpoint(endpointUrl);
			switch (endpoint.getProtocol()) {
			case HTTP: return new TrackerHTTPServer(tracker, endpoint);
			default:
				throw new UnsupportedOperationException("protocol not implemented yet " + endpoint.getProtocol().toString());
			}
		}
	}
	
	public void init() {
		// create tracker
		tracker = new Tracker();
		// create endpoint servers
		servers = new LinkedList<TrackerServer>();
		for (String endpointUrl : config.tracker.endpoints) {
			try {
				TrackerServer server = new TrackerServerFactory(tracker, endpointUrl)
						.create();
				
				servers.add(server);
			} catch (Exception e) {
				logger.error("failed to create endpoint {}: {}", endpointUrl, e.toString());
			}
		}
	}
	
	public void start() {
		// start servers
		for (TrackerServer server : servers) {
			try {
				server.start();
			} catch (Exception e) {
				final String endpointString = server.getEndpoint().getAddress().toString();
				logger.error("failed to start server {}: {}", endpointString, e.toString());
			}
		}
	}
	
	public void stop() {
		// stop servers
		for (TrackerServer server : servers) {
			try {
				server.stop();
			} catch (Exception e) {
				final String endpointString = server.getEndpoint().getAddress().toString();
				logger.error("failed to stop server {}: {}", endpointString, e.toString());
			}
		}
	}
	
	public void mainLoop() {
		// TODO: only for debugging purposes
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("main thread interrupted: {}", e.toString());
			}
		}
	}
}
