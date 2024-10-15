package com.mykola2312.retracker.bencode.error;

import com.mykola2312.retracker.bencode.BValue;

public class BValueError extends BError {
	private static final long serialVersionUID = 6950892783320917930L;

	public BValue node;
	
	public BValueError(BValue node, String message) {
		super(message);
		this.node = node;
	}
}
