package com.mykola2312.retracker.bencode;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.mykola2312.retracker.bencode.error.BDecodeError;
import com.mykola2312.retracker.bencode.error.BDecodeMalformed;
import com.mykola2312.retracker.bencode.error.BError;
import com.mykola2312.retracker.bencode.error.BErrorInvalidKey;

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
	
	@Test
	public void testNestedDict() throws BError {
		final byte[] data = "d5:firstd6:secondd5:thirdi1337eeee".getBytes();
		
		BTree tree = new BTree();
		assertDoesNotThrow(() -> {
			tree.decode(data);
			
			BInteger value = tree
					.asDict()
					.<BDict>get(BType.DICT, "first")
					.<BDict>get(BType.DICT, "second")
					.<BInteger>get(BType.INTEGER, "third");
			assertNotNull(value);
			assertEquals(new BInteger(1337), value);
		});
	}
	
	@Test
	public void testInvalidKeys() {
		assertThrows(BErrorInvalidKey.class, () -> {
			new BTree().setRoot(new BDict()).set(new BList(), new BList());
		});
		
		assertThrows(BDecodeMalformed.class, () -> {
			new BTree().decode("dlelee".getBytes());
		});
	}
	
	@Test
	public void testEncode() {
		assertDoesNotThrow(() -> {
			BTree tree = new BTree();
			
			tree.setRoot(new BInteger(1));
			assertArrayEquals("i1e".getBytes(), tree.encode());
			
			tree.setRoot(new BString("test"));
			assertArrayEquals("4:test".getBytes(), tree.encode());
			
			tree.setRoot(new BDict())
				.set(new BInteger(1), new BInteger(2));
			assertArrayEquals("di1ei2ee".getBytes(), tree.encode());
		});
	}
	
	@Test
	public void testTorrentFile() throws BError, IOException {
		final byte[] data = Files.readAllBytes(Path.of("test", "test.torrent"));
		
		BTree torrent = new BTree();
		assertDoesNotThrow(() -> {
			torrent.decode(data);
			
			BDict root = torrent.asDict();
			assertEquals(new BString("http://example.com/announce"), root.<BString>get(BType.STRING, "announce"));
			assertEquals(new BString("Test Comment"), root.<BString>get(BType.STRING, "comment"));
			assertEquals(new BString("qBittorrent v4.6.5"), root.<BString>get(BType.STRING, "created by"));
			assertEquals(new BInteger(1729033917), root.<BString>get(BType.INTEGER, "creation date"));
			
			BInteger pieceLength = root
					.<BDict>get(BType.DICT, "info")
					.<BDict>get(BType.DICT, "file tree")
					.<BDict>get(BType.DICT, "random-data.bin")
					.<BDict>get(BType.DICT, "")
					.<BInteger>get(BType.INTEGER, "length");
			assertEquals(new BInteger(16777216), pieceLength);
		});
	}
	
	@Test
	public void testDecodeEncodeTorrentFile() throws BError, IOException {
		final byte[] data = Files.readAllBytes(Path.of("test", "test.torrent"));
		
		BTree torrent = new BTree();
		assertDoesNotThrow(() -> {
			torrent.decode(data);
			
			assertArrayEquals(data, torrent.encode());
		});
	}
}
