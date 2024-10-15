package com.mykola2312.retracker.bencode;

import java.util.Iterator;

import com.mykola2312.retracker.bencode.error.BErrorValueCast;

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
		// do not allow any nulls pass into list, because it fucks up everything
		if (item == null) {
			// fail earlier than fail horribly
			throw new NullPointerException();
		}
		
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
	
	@SuppressWarnings("unchecked")
	public <T extends BValue> T get(BType type, int index) throws IndexOutOfBoundsException, BErrorValueCast {
		BValue value = get(index);
		if (!value.getType().equals(type)) {
			throw new BErrorValueCast(this, Integer.toString(index), type, value.getType());
		}
		
		return (T)value;
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
