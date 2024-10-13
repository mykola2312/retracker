package com.mykola2312.retracker.bencode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class BListTest {
	@Test
	public void testEmptyList() {
		BList empty = new BList();
		
		assertEquals(0, empty.getLength());
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
		
		assertEquals(new BInteger(1), list.get(0));
		assertEquals(new BInteger(2), list.get(1));
		assertEquals(new BInteger(3), list.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
	}
}
