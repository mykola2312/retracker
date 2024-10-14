package com.mykola2312.retracker.bencode.error;

public class BDecodeError extends Exception {
	private static final long serialVersionUID = 4282658520481186036L;
	
	public byte[] data;
	public int offset;
	
	public BDecodeError(byte[] data, int offset, String error) {
		super(error);
		this.data = data;
		this.offset = offset;
	}
	
	public BDecodeError(byte[] data, int offset) {
		super(String.format("Error parsing data at offset %d", offset));
		this.data = data;
		this.offset = offset;
	}
}
