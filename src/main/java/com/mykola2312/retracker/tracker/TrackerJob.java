package com.mykola2312.retracker.tracker;

public interface TrackerJob {
	void start();
	void stop();
	Tracker getTracker();
}
