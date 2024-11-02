package com.mykola2312.retracker.tracker;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TrackerHTTPServer implements TrackerServer {
	private static final Logger logger = LoggerFactory.getLogger(TrackerHTTPServer.class);
	
	private Tracker tracker;
	private TrackerEndpoint endpoint;
	private HttpServer httpServer = null;
	
	public TrackerHTTPServer(Tracker tracker, TrackerEndpoint endpoint) {
		this.tracker = tracker;
		this.endpoint = endpoint;
	}
	
	private class AnnounceHTTPHandler implements HttpHandler {
		private static final Logger logger = LoggerFactory.getLogger(AnnounceHTTPHandler.class);
		
		private Tracker tracker;
		
		public AnnounceHTTPHandler(Tracker tracker) {
			this.tracker = tracker;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange.getRequestMethod().equals("GET")) {
				handleAnnounce(exchange);
			}
		}
		
		private void handleAnnounce(HttpExchange exchange) throws IOException {
			// here we need parse URI parameters and pass them to PeerLocalData
			logger.info(exchange.getRequestURI().toString());
		}
	}
	
	@Override
	public void start() {
		try {
			httpServer = HttpServer.create(endpoint.getAddress(), 0);
			
			AnnounceHTTPHandler handler = new AnnounceHTTPHandler(getTracker());
			httpServer.createContext("/", handler);
			
			httpServer.start();
		} catch (IOException e) {
			logger.error("HttpServer: {}", e.toString());
		}
	}

	@Override
	public void stop() {
		if (httpServer != null) {
			httpServer.stop(0);
			httpServer = null;
		}
	}

	@Override
	public Tracker getTracker() {
		return tracker;
	}

	@Override
	public TrackerEndpoint getEndpoint() {
		return endpoint;
	}
}
