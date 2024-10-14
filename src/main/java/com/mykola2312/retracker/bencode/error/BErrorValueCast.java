package com.mykola2312.retracker.bencode.error;

import com.mykola2312.retracker.bencode.BType;
import com.mykola2312.retracker.bencode.BValue;

public class BErrorValueCast extends BValueError {
	private static final long serialVersionUID = 6790204193944478194L;

	public BErrorValueCast(BValue node, String key, BType wanted, BType found) {
		super(node, String.format("cast error: %s is type %s instead of %s", key, found, wanted));
	}
}
