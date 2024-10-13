package com.mykola2312.retracker.bencode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BListTest {
	@Test
	public void testEmptyList() {
		BList empty = new BList();
		
		assertEquals(0, empty.getLength());
		assertNull(empty.getChild());
	}
}
