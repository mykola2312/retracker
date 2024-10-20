package com.mykola2312.retracker.tracker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InfoHashTest {
	@Test
	public void testHashString() {
		final String hashString = "360775c6629eb06e60d90201aed1b7bc49a1ce16";
		
		assertDoesNotThrow(() -> {
			InfoHash hash = new InfoHash();
			
			hash.fromString(hashString);
			assertEquals(hashString, hash.toString());
		});
	}
	
	@Test
	public void testHashEquals() {
		final String hashString = "360775c6629eb06e60d90201aed1b7bc49a1ce16";
		
		assertDoesNotThrow(() -> {
			InfoHash hashOne = new InfoHash();
			InfoHash hashTwo = new InfoHash();
			
			hashOne.fromString(hashString);
			hashTwo.fromString(hashString);
			
			assertEquals(true, hashOne.equals(hashTwo));
		});
	}
	
	@Test
	public void testHashCode() {
		final String hashString = "360775c6629eb06e60d90201aed1b7bc49a1ce16";
		
		assertDoesNotThrow(() -> {
			InfoHash hashOne = new InfoHash();
			InfoHash hashTwo = new InfoHash();
			
			hashOne.fromString(hashString);
			hashTwo.fromString(hashString);
			
			assertEquals(true, hashOne.hashCode() == hashTwo.hashCode());
		});
	}
	
	@Test
	public void testURLEncode() {
		final String hashString = "360775c6629eb06e60d90201aed1b7bc49a1ce16";
		
		assertDoesNotThrow(() -> {
			InfoHash first = new InfoHash();
			first.fromString(hashString);
			
			InfoHash second = new InfoHash();
			second.fromURLEncoded(first.toURLEncoded());
			
			System.out.printf("url encoded hash: %s\n", first.toURLEncoded());
			
			assertEquals(first, second);
		});
	}
}
