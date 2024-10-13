package com.mykola2312.retracker.bencode;

abstract public class BValue {
	private BType type;
	private BValue next = null;
	private BValue child = null;
	
	protected BValue(BType type) {
		this.type = type;
	}
	
	public BType getType() {
		return type;
	}
	
	public BValue getNext() {
		return next;
	}
	
	public BValue getChild() {
		return child;
	}
}