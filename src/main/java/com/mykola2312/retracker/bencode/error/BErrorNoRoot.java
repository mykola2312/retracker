package com.mykola2312.retracker.bencode.error;

public class BErrorNoRoot extends BError {
	private static final long serialVersionUID = -7691652539624483490L;

	public BErrorNoRoot() {
		super("BTree has no root");
	}
}
