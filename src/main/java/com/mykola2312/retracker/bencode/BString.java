package com.mykola2312.retracker.bencode;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BString extends BValue {
	private byte[] bytes;
	
	public BString(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public BString(String text) {
		this.bytes = text.getBytes(StandardCharsets.UTF_8);
	}
	
	@Override()
	public boolean compare(BValue other) {
		return Arrays.equals(((BString)other).get(), get());
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
