package com.mykola2312.retracker.bencode.error;

import com.mykola2312.retracker.bencode.BValue;

public class BErrorNoChildren extends BValueError {
	private static final long serialVersionUID = -4503679050993811843L;

	public BErrorNoChildren(BValue node) {
		super(node, "Node has no children");
	}
}
