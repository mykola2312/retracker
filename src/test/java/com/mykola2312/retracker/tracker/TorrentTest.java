package com.mykola2312.retracker.tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TorrentTest {
	private static InetSocketAddress newAddress(String addr, int port) throws UnknownHostException {
		return new InetSocketAddress(InetAddress.getByName(addr), port);
	}
	
	@Test
	public void testUpdatePeers() throws Exception {
		final InfoHash infoHash = new InfoHash();
		infoHash.fromString("360775c6629eb06e60d90201aed1b7bc49a1ce16");
		
		Torrent torrent = new Torrent(infoHash);
		{
			ArrayList<Peer> copy = new ArrayList<Peer>();
			torrent.copyPeers(copy);
			
			assertEquals(0, copy.size());
		}
		
		List<Peer> src = Arrays.asList(new Peer(newAddress("127.0.0.1", 1337)),
										new Peer(newAddress("127.0.0.1", 1338)));
		torrent.putPeers(src);
		{
			ArrayList<Peer> copy = new ArrayList<Peer>();
			torrent.copyPeers(copy);
			
			assertEquals(2, copy.size());
			assertEquals(1337, copy.get(0).getAddress().getPort());
			assertEquals(1338, copy.get(1).getAddress().getPort());
		}
	}
}
