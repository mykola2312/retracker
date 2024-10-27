package com.mykola2312.retracker.tracker;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;

public class PeerSet extends HashMap<InetSocketAddress, Peer> implements Iterable<Peer> {
	private static final long serialVersionUID = -6175727375850698818L;

	public PeerSet() {
		super();
	}
	
	public PeerSet(int initialCapacity) {
		super(initialCapacity);
	}
	
	/* Peer add contract: will override existing peer
	 * only in favor of new locally announced peer,
	 * in other words PeerType.LOCAL will always overwrite PeerType.REMOTE
	 */
	public void put(Peer peer) {
		InetSocketAddress key = peer.getAddress();
		// check for existing peer
		Peer found = get(key);
		if (found != null && found.getType() == PeerType.REMOTE) {
			remove(key);
		}
		
		put(key, peer);
	}
	
	public Peer get(InetSocketAddress addr) {
		return super.get(addr);
	}
	
	public Peer get(Peer peer) {
		return get(peer.getAddress());
	}
	
	@Override
	public Iterator<Peer> iterator() {
		return values().iterator();
	}
}
