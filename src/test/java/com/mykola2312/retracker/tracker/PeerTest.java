package com.mykola2312.retracker.tracker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

public class PeerTest {
	private static InetSocketAddress newAddress(String addr, int port) throws UnknownHostException {
		return new InetSocketAddress(InetAddress.getByName(addr), port);
	}
	
	@Test
	public void testPeersEqual() {
		assertDoesNotThrow(() -> {
			// remote and remote peer
			assertEquals(new Peer(newAddress("69.69.69.69", 1337)),
						new Peer(newAddress("69.69.69.69", 1337)));
			
			// remote and local peer
			assertEquals(new Peer(newAddress("69.69.69.69", 1337)),
					new Peer(newAddress("69.69.69.69", 1337), null));
		});
	}
}
