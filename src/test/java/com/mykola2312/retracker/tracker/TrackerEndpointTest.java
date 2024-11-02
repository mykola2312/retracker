package com.mykola2312.retracker.tracker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TrackerEndpointTest {
	@Test
	public void parseTrackerUrlTest() {
		assertDoesNotThrow(() -> {
			TrackerEndpoint endpoint = TrackerEndpoint.parseEndpoint("http://example.com");
			assertEquals(TrackerProtocol.HTTP, endpoint.getProtocol());
			assertEquals("example.com", endpoint.getAddress().getHostName());
			assertEquals(80, endpoint.getAddress().getPort());
		});
		
		assertDoesNotThrow(() -> {
			TrackerEndpoint endpoint = TrackerEndpoint.parseEndpoint("http://example.com/");
			assertEquals(TrackerProtocol.HTTP, endpoint.getProtocol());
			assertEquals("example.com", endpoint.getAddress().getHostName());
			assertEquals(80, endpoint.getAddress().getPort());
		});
		
		assertDoesNotThrow(() -> {
			TrackerEndpoint endpoint = TrackerEndpoint.parseEndpoint("http://example.com:80");
			assertEquals(TrackerProtocol.HTTP, endpoint.getProtocol());
			assertEquals("example.com", endpoint.getAddress().getHostName());
			assertEquals(80, endpoint.getAddress().getPort());
		});
		
		assertDoesNotThrow(() -> {
			TrackerEndpoint endpoint = TrackerEndpoint.parseEndpoint("http://example.com:80/");
			assertEquals(TrackerProtocol.HTTP, endpoint.getProtocol());
			assertEquals("example.com", endpoint.getAddress().getHostName());
			assertEquals(80, endpoint.getAddress().getPort());
		});
		
		assertDoesNotThrow(() -> {
			TrackerEndpoint endpoint = TrackerEndpoint.parseEndpoint("udp://example.com");
			assertEquals(TrackerProtocol.UDP, endpoint.getProtocol());
			assertEquals("example.com", endpoint.getAddress().getHostName());
			assertEquals(TrackerProtocol.UDP.getDefaultPort(), endpoint.getAddress().getPort());
		});
		
		assertDoesNotThrow(() -> {
			TrackerEndpoint endpoint = TrackerEndpoint.parseEndpoint("udp://127.0.0.1:1337");
			assertEquals(TrackerProtocol.UDP, endpoint.getProtocol());
			assertEquals("localhost", endpoint.getAddress().getHostName());
			assertEquals(1337, endpoint.getAddress().getPort());
		});
	}
}
