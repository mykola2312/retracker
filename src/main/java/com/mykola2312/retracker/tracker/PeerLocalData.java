package com.mykola2312.retracker.tracker;

/* This data is available only from local announces,
 * It shall be used for re-announces
 */
public class PeerLocalData {
	private long downloaded = 0;	// 8 bytes integer
	private long left = 0;			// 8 bytes integer
	private long uploaded = 0;		// 8 bytes integer
	private int event = 0;			// 4 bytes integer, enum
	private long key = 0;			// 4 bytes integer
}
