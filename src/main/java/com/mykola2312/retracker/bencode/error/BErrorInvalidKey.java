package com.mykola2312.retracker.bencode.error;

import com.mykola2312.retracker.bencode.BValue;

public class BErrorInvalidKey extends BValueError {
	private static final long serialVersionUID = 8821935501326704941L;

	public BErrorInvalidKey(BValue node, BValue key, String message) {
		super(node, String.format("key \"%s\" invalid: %s", key, message));
	}
}
