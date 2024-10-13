package com.mykola2312.retracker.bencode;

public class BInteger extends BValue {
	private long value;
	
	public BInteger(long value) {
		this.value = value;
	}
	
	@Override()
	public boolean compare(BValue other) {
		return get() == ((BInteger)other).get();
	}
	
	@Override()
	public BType getType() {
		return BType.INTEGER;
	}
	
	public long get() {
		return value;
	}
}
