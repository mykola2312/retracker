package com.mykola2312.retracker.tracker;

import java.util.Collection;

public class Torrent {
	private InfoHash infoHash;
	private PeerSet peers;
	
	public Torrent(InfoHash infoHash) {
		this.infoHash = infoHash;
		this.peers = new PeerSet();
	}
	
	// will construct PeerSet with initial single peer
	public Torrent(InfoHash infoHash, Peer initiator) {
		this.infoHash = infoHash;
		this.peers = new PeerSet(1);
		peers.put(initiator);
	}
	
	public InfoHash getInfoHash() {
		return infoHash;
	}
	
	/* Copy current set of peers into destination collection.
	 * Copying over reference is preferable in concurrent environment
	 */
	public <T extends Collection<Peer>> void copyPeers(T dst) {
		for (Peer peer : peers) {
			dst.add(peer);
		}
	}
	
	public <T extends Collection<Peer>> void putPeers(T src) {
		for (Peer peer : src) {
			peers.put(peer);
		}
	}
}
