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
	
	// other is guaranteed to be same BType
	abstract public boolean compare(BValue other);
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof BValue)) return false;
		
		BValue other = (BValue)o;
		if (other.getType() != getType()) return false;
		
		return compare(other);
	}
	
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