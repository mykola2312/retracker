package com.mykola2312.retracker.bencode;

import java.util.Iterator;

public class BValueIterator implements Iterator<BValue> {
	private BValue node;
	public BValueIterator(BValue head) {
		this.node = head;
	}
	
	public boolean hasNext() {
		return node != null;
	}
	
	public BValue next() {
		BValue current = node;
		node = current.getNext();
		return current;
	}
}
