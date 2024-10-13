package com.mykola2312.retracker.bencode;

import java.nio.charset.StandardCharsets;

public class BString extends BValue {
	private byte[] bytes;
	
	public BString(byte[] bytes) {
		this.bytes = bytes;
	}
	
	@Override()
	public BType getType() {
		return BType.STRING;
	}
	
	public byte[] get() {
		return bytes;
	}
	
	@Override()
	public String toString() {
		return new String(bytes, StandardCharsets.UTF_8);
	}
}
