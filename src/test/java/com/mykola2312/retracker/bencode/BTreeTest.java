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
			assertEquals(new BInteger(696969), root);
			
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
			assertEquals(0, ((BList)root).getLength());
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
				assertEquals(new BInteger(1), list.get(BType.INTEGER, 0));
				assertEquals(new BInteger(2345678), list.get(BType.INTEGER, 1));
				
				BInteger value = list
						.<BList>get(BType.LIST, 2)
						.<BInteger>get(BType.INTEGER, 0);
				assertNotNull(value);
				assertEquals(new BInteger(69), value);
				System.out.println("value from nested list: " + value);
			});
		});
	}
	
	@Test
	public void testString() throws BDecodeError {
		final byte[] data = "11:testString!".getBytes();
		
		BTree tree = new BTree();
		assertDoesNotThrow(() -> {
			tree.decode(data);
			
			assertNotNull(tree.getRoot());
			
			BString string = (BString)tree.getRoot();
			System.out.println("string: " + string);
			assertEquals(new BString("testString!"), string);
		});
	}
	
	@Test
	public void testDict() throws BDecodeError {
		final byte[] data = "di0e5:first6:secondi69ee".getBytes();
		
		BTree tree = new BTree();
		assertDoesNotThrow(() -> {
			tree.decode(data);
			
			BDict root = tree.asDict();
			// check keys
			assertEquals(new BInteger(0), root.find(new BInteger(0)));
			assertEquals(new BString("second"), root.find(new BString("second")));
			// check values
			assertEquals(new BString("first"), root.get(new BInteger(0)));
			assertEquals(new BInteger(69), root.<BInteger>get(BType.INTEGER, "second"));
		});
	}
}
