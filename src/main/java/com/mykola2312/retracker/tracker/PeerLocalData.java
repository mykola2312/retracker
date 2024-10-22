package com.mykola2312.retracker.tracker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/* This data is available only from local announces,
 * It shall be used for re-announces
 */
public class PeerLocalData {
	private long downloaded;		// 8 bytes integer
	private long left;				// 8 bytes integer
	private long uploaded;			// 8 bytes integer
	private AnnounceEvent event;	// 4 bytes integer
	private long key;				// 4 bytes integer
	/* Other values gathered from local announce, must NOT include
	 * info_hash, ip, port, downloaded, left, uploaded, event.
	 * Because they primarily parsed by Announce parser itself, and
	 * must be set in primitive members above.
	 */
	private static final HashSet<String> OTHER_IGNORE = new HashSet<String>(Arrays.asList(
			"info_hash", "ip", "port", "downloaded", "left", "uploaded", "left"
			));
	
	private HashMap<String, String> other = new HashMap<>();
	
	public PeerLocalData(long downloaded, long left, long uploaded, AnnounceEvent event, long key) {
		this.downloaded = downloaded;
		this.left = left;
		this.uploaded = uploaded;
		this.event = event;
		this.key = key;
	}
	
	public void setOtherValue(String name, String value) {
		// ensure that value is not ignored
		if (!OTHER_IGNORE.contains(name)) {
			other.put(name, value);
		}
	}
	
	public String getOtherValue(String name) {
		return other.get(name);
	}
	
	public long getDownloaded() {
		return this.downloaded;
	}
	
	public long getLeft() {
		return this.left;
	}
	
	public long getUploaded() {
		return this.uploaded;
	}
	
	public AnnounceEvent getEvent() {
		return this.event;
	}
	
	public long getKey() {
		return this.key;
	}
}
