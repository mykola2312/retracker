package com.mykola2312.retracker.tracker;

public enum TrackerProtocol {
	HTTP,
	UDP;
	
	public int getDefaultPort() {
		switch (this) {
		case HTTP:	return 80;
		case UDP:	return 2710;
		default:	return 0;
		}
	}
}
