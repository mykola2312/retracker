package com.mykola2312.retracker.bencode.error;

import com.mykola2312.retracker.bencode.BValue;

public class BError extends Exception {
	private static final long serialVersionUID = 6950892783320917930L;

	public BValue node;
	
	public BError(BValue node, String message) {
		super(message);
		this.node = node;
	}
}
