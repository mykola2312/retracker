package com.mykola2312.retracker.bencode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class EqualsTest {
	@Test
	public void testIntegerEquals() {
		assertEquals(new BInteger(1), new BInteger(1));
		assertNotEquals(new BInteger(2), new BInteger(3));
	}
	
	@Test
	public void testStringEquals() {
		assertEquals(new BString("first"), new BString("first"));
		assertNotEquals(new BString("first"), new BString("second"));
	}
}
