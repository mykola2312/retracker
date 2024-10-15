package com.mykola2312.retracker.bencode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mykola2312.retracker.bencode.error.BDecodeError;
import com.mykola2312.retracker.bencode.error.BDecodeMalformed;

public class BTreeTest {
	@Test
	public void testParseInt() throws BDecodeError {
		final byte[] data = "i696969e".getBytes();
		
		BTree tree = new BTree();
		assertDoesNotThrow(() -> {
			tree.decode(data);
			
			BValue root = tree.getRoot();
			assertNotNull(root);
			assertEquals(root, new BInteger(696969));
			
			System.out.println("testParseInt: " + root.toString());
		});
	}
	
	@Test
	public void testParseIntOverrun() throws BDecodeError {
		final byte[] data = "i696969".getBytes();
		
		BTree tree = new BTree();
		assertThrows(BDecodeMalformed.class, () -> {
			tree.decode(data);
		});
	}
	
	@Test
	public void testEmptyList() throws BDecodeError {
		final byte[] data = "le".getBytes();
		
		BTree tree = new BTree();
		assertDoesNotThrow(() -> {
			tree.decode(data);
			
			BValue root = tree.getRoot();
			assertNotNull(root);
			assertEquals(((BList)root).getLength(), 0);
		});
	}
	
	@Test
	public void testList() throws BDecodeError {
		final byte[] data = "li1ei2345678eli69eee".getBytes();
		
		BTree tree = new BTree();
		assertDoesNotThrow(() -> {
			tree.decode(data);
			
			assertNotNull(tree.getRoot());
			
			BList list = (BList)tree.getRoot();
			{
				System.out.println(String.format("BDecode'd list size: %d", list.getLength()));
				System.out.println("BDecode'd list contents:");
				for (BValue item : list) {
					System.out.println(item.toString());
				}
			}
			assertDoesNotThrow(() -> {
				assertEquals(list.get(BType.INTEGER, 0), new BInteger(1));
				assertEquals(list.get(BType.INTEGER, 1), new BInteger(2345678));
				
				BInteger value = list
						.<BList>get(BType.LIST, 2)
						.<BInteger>get(BType.INTEGER, 0);
				assertNotNull(value);
				assertEquals(value, new BInteger(69));
				System.out.println("value from nested list: " + value);
			});
		});
	}
}
