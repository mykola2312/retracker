package com.mykola2312.retracker.bencode.error;

import com.mykola2312.retracker.bencode.BValue;

public class BErrorValueCast extends BValueError {
	private static final long serialVersionUID = 6790204193944478194L;

	public BErrorValueCast(BValue node, String key, Class cause) {
		super(node, String.format("cast error: %s is instance of %s", key, cause.getName()));
	}
}
