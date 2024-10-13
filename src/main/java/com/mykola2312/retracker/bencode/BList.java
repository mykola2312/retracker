package com.mykola2312.retracker.bencode;

import java.util.Iterator;

public class BList extends BValue {
	private BValue last = null;
	private int length = 0;
	
	@Override()
	public BType getType() {
		return BType.LIST;
	}
	
	@Override()
	public boolean compare(BValue other) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	protected BValue getFirst() {
		return getChild();
	}
	
	protected void setFirst(BValue first) {
		setChild(first);
	}
	
	protected BValue getLast() {
		return last;
	}
	
	protected void setLast(BValue last) {
		this.last = last;
	}
	
	// builder
	public BList append(BValue item) {
		BValue first = getFirst();
		BValue last = getLast();
		if (first == null) {
			setFirst(item);
			setLast(item);
		} else {
			last.setNext(item);
			setLast(item);
		}
		
		length++;
		return this;
	}
	
	public int getLength() {
		return length;
	}
	
	public BValue get(int index) throws IndexOutOfBoundsException {
		Iterator<BValue> it = iterator();
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException();
		}
		
		while (index-- > 0) {
			it.next();
		}
		
		return it.next();
	}
	
	public BValue find(BValue key) {
		if (getLength() == 0) {
			return null;
		}
		
		for (BValue node : this) {
			if (node.equals(key)) {
				return node;
			}
		}
		
		return null;
	}
}
