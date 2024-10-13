package com.mykola2312.retracker.bencode.error;

import com.mykola2312.retracker.bencode.BValue;

public class BErrorKeyNotFound extends BValueError {
	private static final long serialVersionUID = 4532538286014234752L;

	public BErrorKeyNotFound(BValue node, BValue key) {
		super(node, key.toString());
	}
}
