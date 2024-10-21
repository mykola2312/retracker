package com.mykola2312.retracker.tracker;

public enum AnnounceEvent {
	NONE(0),
	STARTED(1),
	STOPPED(2),
	COMPLETED(3);
	
	private int value;
	
	AnnounceEvent() {
		this.value = 0;
	}
	
	AnnounceEvent(int value) {
		this.value = value;
	}
	
	public static AnnounceEvent fromString(String value) {
		switch (value) {
		case "none":		return AnnounceEvent.NONE;
		case "started":		return AnnounceEvent.STARTED;
		case "stopped":		return AnnounceEvent.STOPPED;
		case "completed":	return AnnounceEvent.COMPLETED;
		default:			return null;
		}
	}
	
	public static AnnounceEvent fromInteger(int value) {
		switch (value) {
		case 0:				return AnnounceEvent.NONE;
		case 1:				return AnnounceEvent.STARTED;
		case 2:				return AnnounceEvent.STOPPED;
		case 3:				return AnnounceEvent.COMPLETED;
		default:			return null;
		}
	}
	
	@Override
	public String toString() {
		switch (this) {
		case STARTED:		return "started";
		case STOPPED:		return "stopped";
		case COMPLETED:		return "completed";
		default:			return null;
		}
	}
	
	public int toInteger() {
		return this.value;
	}
}
