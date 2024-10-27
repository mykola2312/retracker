package com.mykola2312.retracker.tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class PeerSetTest {
	private static InetSocketAddress newAddress(String addr, int port) throws UnknownHostException {
		return new InetSocketAddress(InetAddress.getByName(addr), port);
	}
	
	@Test
	public void testPeerAddContract() throws UnknownHostException {
		PeerSet set = new PeerSet(1);
		
		set.put(new Peer(newAddress("127.0.0.1", 1337))); // remote
		assertEquals(PeerType.REMOTE, set.get(newAddress("127.0.0.1", 1337)).getType());
		
		// override with local peer
		set.put(new Peer(newAddress("127.0.0.1", 1337), null)); // local
		assertEquals(PeerType.LOCAL, set.get(newAddress("127.0.0.1", 1337)).getType());
	}
	
	@Test
	public void testIterator() throws UnknownHostException {
		PeerSet set = new PeerSet(2);
		set.put(new Peer(newAddress("127.0.0.1", 1337)));
		set.put(new Peer(newAddress("127.0.0.1", 1338)));
		
		Iterator<Peer> it = set.iterator();
		
		assertEquals(true, it.hasNext());
		assertEquals(1337, it.next().getAddress().getPort());
		
		assertEquals(true, it.hasNext());
		assertEquals(1338, it.next().getAddress().getPort());
	}
}
