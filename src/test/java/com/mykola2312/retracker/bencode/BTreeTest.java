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
}
