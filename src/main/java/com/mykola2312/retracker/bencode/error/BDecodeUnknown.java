package com.mykola2312.retracker.bencode.error;

public class BDecodeUnknown extends BDecodeError {
	private static final long serialVersionUID = -7027240782119100714L;
	
	byte unknown;
	
	public BDecodeUnknown(byte[] data, int offset, byte unknown) {
		super(data, offset, String.format("Unknown symbol 0x%x at offset %d", unknown, offset));
		this.unknown = unknown;
	}
}
