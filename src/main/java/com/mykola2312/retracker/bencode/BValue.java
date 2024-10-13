package com.mykola2312.retracker.bencode;

import java.util.Iterator;

/* Base class for every type of bencode value, as tree nodes,
 * simple types like integer or string will have next pointing
 * to next sibling and have no child, where list's child points
 * to linked values - items of list. In dict situation, child points
 * to linked list of keys, where each key has child - value
 */

abstract public class BValue implements Iterable<BValue> {
	private BValue next = null;
	private BValue child = null;
	
	abstract public BType getType();
	
	public BValue getNext() {
		return next;
	}
	
	public void setNext(BValue next) {
		this.next = next;
	}
	
	public BValue getChild() {
		return child;
	}
	
	public void setChild(BValue child) {
		this.child = child;
	}
	
	public Iterator<BValue> iterator() {
		return new BValueIterator(child);
	}
}