package com.mykola2312.retracker.tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class AnnounceEventTest {
	@Test
	public void testEventEqual() {
		assertEquals(AnnounceEvent.NONE, AnnounceEvent.NONE);
		assertEquals(AnnounceEvent.STARTED, AnnounceEvent.STARTED);
	}
	
	@Test
	public void testEventNotEqual() {
		assertNotEquals(AnnounceEvent.STOPPED, AnnounceEvent.STARTED);
	}
}
