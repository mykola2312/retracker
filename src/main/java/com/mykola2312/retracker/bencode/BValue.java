package com.mykola2312.retracker.bencode;

import java.util.Iterator;

/* Base class for every type of bencode value, as tree nodes,
 * simple types like integer or string will have next pointing
 * to next sibling and have no child, where list's child points
 * to linked values - items of list. In dict situation, child points
 * to linked list of keys, where each key has child - value
 */

abstract public class BValue implements Iterable<BValue> {
	private BType type;
	private BValue next = null;
	private BValue child = null;
	
	protected BValue(BType type) {
		this.type = type;
	}
	
	public BType getType() {
		return type;
	}
	
	public BValue getNext() {
		return next;
	}
	
	public BValue getChild() {
		return child;
	}
	
	public Iterator<BValue> iterator() {
		return new BValueIterator(child);
	}
}