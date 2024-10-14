package com.mykola2312.retracker.bencode;

import com.mykola2312.retracker.bencode.error.BErrorKeyNotFound;
import com.mykola2312.retracker.bencode.error.BErrorNoChildren;
import com.mykola2312.retracker.bencode.error.BErrorValueCast;
import com.mykola2312.retracker.bencode.error.BValueError;

public class BDict extends BList {
	@Override()
	public BType getType() {
		return BType.DICT;
	}
	
	public BDict set(BValue key, BValue value) {
		BValue node = find(key);
		if (node != null) {
			node.setNext(value);
		} else {
			key.setChild(value);
			append(key);
		}
		
		return this;
	}
	
	public BDict set(String key, BValue value) {
		return set(new BString(key), value);
	}
	
	/* since we're going to employ builder pattern, we can't return null.
	 * chaining gets in one try-catch is better than checking every return value
	 */
	public BValue get(BValue key) throws BValueError {
		BValue node = find(key);
		if (node == null) throw new BErrorKeyNotFound(this, key);
		
		BValue value = node.getChild();
		if (value == null) throw new BErrorNoChildren(node);
		
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BValue> T get(BType type, String key) throws BValueError {
		BValue value = get(new BString(key));
		if (!value.getType().equals(type)) {
			throw new BErrorValueCast(this, key, type, value.getType());
		}
		
		return (T)value;
	}
}
