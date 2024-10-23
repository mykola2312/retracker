package com.mykola2312.retracker.tracker;

import java.net.InetSocketAddress;

/* Local peers are ones who announced to this tracker:
 * local data is their announced data. Remote peers on the other hand
 * are peers gathered from destination trackers in re-announce job.
 * Local peers and their data, when being matched by specific property,
 * are used to form re-announce announce request to destination tracker.
 */
public class Peer {
	private PeerType type;
	private InetSocketAddress address;
	private PeerLocalData data;
	
	/* By providing two constructors, where one just has address, and
	 * other one takes address and local data, we can leverage
	 * address and local data parsing to Announce parser, instead of
	 * dealing with parsing errors in constructors
	 */
	
	// remote peer
	public Peer(InetSocketAddress address) {
		this.type = PeerType.REMOTE;
		this.address = address;
		this.data = null;
	}
	
	// local peer
	public Peer(InetSocketAddress address, PeerLocalData data) {
		this.type = PeerType.LOCAL;
		this.address = address;
		this.data = data;
	}
	
	public PeerType getType() {
		return type;
	}
	
	public InetSocketAddress getAddress() {
		return address;
	}
	
	public PeerLocalData getData() {
		return data;
	}
	
	/* What matters in peer for trackers is his IP:Port, so
	 * equals and hashCode must be a proxy for InetSocketAddress.
	 * Later we're gonna match by IP:Port in event for peer set update
	 * and PREFER the peer with local data.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Peer)) return false;
		Peer other = (Peer)o;
		return address.equals(other.address);
	}
	
	@Override
	public final int hashCode() {
		return address.hashCode();
	}
}
