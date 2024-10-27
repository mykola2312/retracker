package com.mykola2312.retracker.tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TrackerTest {
	private static InetSocketAddress newAddress(String addr, int port) throws UnknownHostException {
		return new InetSocketAddress(InetAddress.getByName(addr), port);
	}
	
	class TrackerJob implements Runnable {
		private Tracker tracker;
		private InfoHash infoHash;
		private Peer peer;
		
		public TrackerJob(Tracker tracker, InfoHash infoHash, Peer peer) {
			this.tracker = tracker;
			this.infoHash = infoHash;
			this.peer = peer;
		}

		@Override
		public void run() {
			tracker.announcePeer(infoHash, peer);
		}
	}
	
	@Test
	public void testTrackerPutAndGet() throws Exception {
		final InfoHash infoHash = new InfoHash();
		infoHash.fromString("360775c6629eb06e60d90201aed1b7bc49a1ce16");
		
		Tracker tracker = new Tracker();
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 32; i++) {
			TrackerJob job = new TrackerJob(tracker, infoHash,
					new Peer(newAddress("127.0.0.1", 1337 + i)));
			Thread thread = new Thread(job);
			thread.start();
			threads.add(thread);
			
		}
		
		// wait for all threads to finish
		for (Thread thread : threads) {
			thread.join();
		}
		
		ArrayList<Peer> peers = new ArrayList<Peer>(32);
		tracker.getPeers(infoHash, peers);
		
		assertEquals(32, peers.size());
		
		// check if every port present
		for (int i = 0; i < 32; i++) {
			int port = 1337 + i;
			boolean peerPortPresent = false;
			for (Peer peer : peers) {
				if (peer.getAddress().getPort() == port) {
					peerPortPresent = true;
				}
			}
			
			assertEquals(true, peerPortPresent);
		}
	}
}
