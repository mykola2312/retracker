package com.mykola2312.retracker.tracker;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class TrackerEndpoint {
	private TrackerProtocol protocol;
	private InetSocketAddress address;
	
	private TrackerEndpoint(TrackerProtocol protocol, InetSocketAddress address) {
		this.protocol = protocol;
		this.address = address;
	}
	
	public TrackerProtocol getProtocol() {
		return protocol;
	}
	
	public InetSocketAddress getAddress() {
		return address;
	}
	
	static class TrackerEndpointException extends Exception {
		private static final long serialVersionUID = 284186934690560946L;

		public TrackerEndpointException(String url, String message) {
			super(String.format("malformed tracker url %s, error: %s", url, message));
		}
	}
	
	public static TrackerEndpoint parseEndpoint(String url) throws TrackerEndpointException {
		int hostnameIdx = 0;
		TrackerProtocol protocol;
		if (url.startsWith("http://")) {
			hostnameIdx = 7;
			protocol = TrackerProtocol.HTTP;
		} else if (url.startsWith("udp://")) {
			hostnameIdx = 6;
			protocol = TrackerProtocol.UDP;
		} else {
			throw new TrackerEndpointException(url, "unknown tracker protocol");
		}
		
		String part1 = url.substring(hostnameIdx);
		if (part1.isEmpty() || part1.isBlank()) {
			throw new TrackerEndpointException(url, "no hostname after protocol");
		}
		
		String[] hostnameAndUri = part1.split("/");
		String hostname = (hostnameAndUri != null && hostnameAndUri.length > 0)
				? hostnameAndUri[0] : part1;
		
		if (hostname.isEmpty() || hostname.isBlank()) {
			throw new TrackerEndpointException(url, "no hostname");
		}
		
		String[] hostnameAndPort = hostname.split(":");
		try {
			int port;
			InetSocketAddress address;
			
			if (hostnameAndPort != null && hostnameAndPort.length > 1) {
				// parse port from url
				port = Integer.parseInt(hostnameAndPort[1]);
				address = new InetSocketAddress(InetAddress.getByName(hostnameAndPort[0]), port);
			} else {
				// use default port for protocol
				port = protocol.getDefaultPort();
				address = new InetSocketAddress(InetAddress.getByName(hostname), port);
			}
			
			return new TrackerEndpoint(protocol, address);
		} catch (NumberFormatException e) {
			throw new TrackerEndpointException(url, "failed to parse port");
		} catch (UnknownHostException e) {
			throw new TrackerEndpointException(url, e.toString());
		}
	}
}
