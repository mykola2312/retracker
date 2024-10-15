package com.mykola2312.retracker.bencode.error;

public class BDecodeParseError extends BDecodeError {
	private static final long serialVersionUID = 8987586062482975916L;

	public BDecodeParseError(byte[] data, int offset, Throwable cause, String offense) {
		super(data, offset, String.format("Failed to parse into primitive type at offset %d: %s", offset, offense));
		initCause(cause);
	}
}
