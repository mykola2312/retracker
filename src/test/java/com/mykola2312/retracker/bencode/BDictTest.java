package com.mykola2312.retracker.bencode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mykola2312.retracker.bencode.error.BErrorKeyNotFound;
import com.mykola2312.retracker.bencode.error.BErrorValueCast;
import com.mykola2312.retracker.bencode.error.BValueError;

public class BDictTest {
	@Test
	public void testKeyValue() throws BValueError {
		BDict dict = new BDict();
		
		dict.set(new BString("first"), new BInteger(1));
		assertNotNull(dict.find(new BString("first")));
		assertNull(dict.find(new BString("second")));
		assertEquals(dict.get(new BString("first")), new BInteger(1));
		System.out.println(dict.get(new BString("first")).toString());
		
		dict.set(new BString("second"), new BInteger(2));
		assertEquals(dict.get(new BString("second")), new BInteger(2));
	}
	
	@Test
	public void testKeyNotFound() {
		BDict empty = new BDict();
		
		assertThrows(BErrorKeyNotFound.class, () -> {
			empty.get(new BString("non-existent"));
		});
	}
	
	@Test
	public void testKeyChild() {
		BDict dict = new BDict();
		dict.set(new BInteger(1), new BInteger(2));
		
		BValue node = dict.find(new BInteger(1));
		assertNotNull(node);
		assertNotNull(node.getChild());
		assertEquals(node.getChild(), new BInteger(2));
	}
	
	@Test
	public void testCastError() {
		BDict dict = new BDict();
		dict.set("key", new BInteger(4));
		
		assertThrows(BErrorValueCast.class, () -> {
			dict.<BString>get("key").get();
		});
	}
}
