package com.mykola2312.retracker.tracker;

public enum AnnounceEvent {
	NONE(0),
	STARTED(1),
	STOPPED(2),
	COMPLETED(3);
	
	private int value;
	
	AnnounceEvent(int value) {
		this.value = value;
	}
	
	public static AnnounceEvent fromEventString(String value) throws Exception {
		switch (value) {
		case "none":		return AnnounceEvent.NONE;
		case "started":		return AnnounceEvent.STARTED;
		case "stopped":		return AnnounceEvent.STOPPED;
		case "completed":	return AnnounceEvent.COMPLETED;
		default:			throw new Exception("unknown event: " + value);
		}
	}
	
	public static AnnounceEvent fromEventValue(int value) throws Exception {
		switch (value) {
		case 0:				return AnnounceEvent.NONE;
		case 1:				return AnnounceEvent.STARTED;
		case 2:				return AnnounceEvent.STOPPED;
		case 3:				return AnnounceEvent.COMPLETED;
		default:			throw new Exception("unknown event: " + value);
		}
	}
	
	public String toEventString() {
		switch (this) {
		case STARTED:		return "started";
		case STOPPED:		return "stopped";
		case COMPLETED:		return "completed";
		default:			throw new RuntimeException("UNEXPECTED event value in AnnounceEvent: " + value);
		}
	}
	
	public int toEventValue() {
		return this.value;
	}
}
