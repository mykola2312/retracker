package com.mykola2312.retracker.tracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* Tracker meant to be called from multiple running threads,
 * so thread-safety ensured by synchronized methods.
 */
public class Tracker {
	private HashMap<InfoHash, Torrent> torrents = new HashMap<InfoHash, Torrent>();
	
	/* Adds peer to torrent's PeerSet. If no such Torrent present,
	 * creates the Torrent.
	 */
	public synchronized void announcePeer(InfoHash infoHash, Peer peer) {
		Torrent torrent = torrents.get(infoHash);
		if (torrent != null) {
			torrent.putPeers(List.of(peer));
		} else {
			// create torrent and initiate PeerSet with first announced peer
			torrent = new Torrent(infoHash, peer);
			torrents.put(torrent.getInfoHash(), torrent);
		}
	}
	
	/* Adds multiple peers to torrent. If no such Torrent present,
	 * creates the torrent.
	 */
	public synchronized <T extends Collection<Peer>> void announcePeers(InfoHash infoHash, T src) {
		Torrent torrent = torrents.get(infoHash);
		if (torrent != null) {
			torrent.putPeers(src);
		} else {
			torrent = new Torrent(infoHash);
			torrent.putPeers(src);
			torrents.put(torrent.getInfoHash(), torrent);
		}
	}
	
	// Get list of peers at this very moment.
	public synchronized <T extends Collection<Peer>> void getPeers(InfoHash infoHash, T dst) {
		Torrent torrent = torrents.get(infoHash);
		if (torrent != null) {
			torrent.copyPeers(dst);
		}
	}
}
