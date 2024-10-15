package com.mykola2312.retracker.bencode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.mykola2312.retracker.bencode.error.BErrorValueCast;

public class BListTest {
	@Test
	public void testEmptyList() {
		BList empty = new BList();
		
		assertEquals(empty.getLength(), 0);
		assertNull(empty.getChild());
	}
	
	@Test
	public void testAppend() {
		BList list = new BList();
		list.append(new BInteger(1));
		list.append(new BInteger(2));
		list.append(new BInteger(3));
		
		System.out.println("manual iteration:");
		{
			BValue cur = list.getChild();
			do {
				System.out.println(((BInteger)cur).get());
				cur = cur.getNext();
			} while (cur != null);
		}
		System.out.println("iterator:");
		{
			Iterator<BValue> it = list.iterator();
			while (it.hasNext()) {
				System.out.println(((BInteger)it.next()).get());
			}
		}
		System.out.println("get:");
		{
			System.out.println(((BInteger)list.get(0)).get());
			System.out.println(((BInteger)list.get(1)).get());
			System.out.println(((BInteger)list.get(2)).get());
		}
		
		assertEquals(list.get(0), new BInteger(1));
		assertEquals(list.get(1), new BInteger(2));
		assertEquals(list.get(2), new BInteger(3));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
	}
	
	@Test
	public void testFind() {
		BList list = new BList();
		list.append(new BInteger(1));
		list.append(new BInteger(2));
		
		assertNotNull(list.find(new BInteger(1)));
		assertNotNull(list.find(new BInteger(2)));
		assertNull(list.find(new BInteger(3)));
	}
	
	@Test
	public void testGet() throws IndexOutOfBoundsException, BErrorValueCast {
		BList list = new BList();
		list.append(new BInteger(1));
		
		assertDoesNotThrow(() -> {
			BInteger value = list.get(BType.INTEGER, 0);
			
			assertNotNull(value);
			assertEquals(new BInteger(1), value);
		});
	}
	
	@Test
	public void testGetWrongCast() throws IndexOutOfBoundsException, BErrorValueCast {
		BList list = new BList();
		list.append(new BInteger(1));
		
		assertThrows(BErrorValueCast.class, () -> {
			BString wrong = list.get(BType.STRING, 0);
		});
	}
}
