package com.mykola2312.retracker.bencode;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.mykola2312.retracker.bencode.error.BDecodeError;
import com.mykola2312.retracker.bencode.error.BDecodeMalformed;
import com.mykola2312.retracker.bencode.error.BDecodeParseError;
import com.mykola2312.retracker.bencode.error.BDecodeUnknown;

public class BTree {
	private BValue root = null;
	
	public BValue getRoot() {
		return root;
	}
	
	class BDecoder {
		private byte[] data;
		private int offset = 0;
		
		public BDecoder(byte[] data, int offset) {
			this.data = data;
			this.offset = offset;
		}
		
		private Long parseLong(byte[] bytes) throws BDecodeParseError {
			String strInt = new String(bytes, StandardCharsets.UTF_8);
			try {
				return Long.parseLong(strInt);
			} catch (NumberFormatException e) {
				throw new BDecodeParseError(data, offset, e);
			}
		}
		
		private static final byte BE_INTEGER = (byte)'i';
		private static final byte BE_LIST = (byte)'l';
		private static final byte BE_DICT = (byte)'d';
		private static final byte BE_STRING_SEP = (byte)':';
		private static final byte BE_END = (byte)'e';
		
		public BValue decode() throws BDecodeError {
			if (data.length - offset < 2) {
				/* no bencode data can be less than 2 bytes: for integer must be 'i0e' atleast,
				 * strings must be at least '0:' and lists and dicts can be empty
				 */
				throw new BDecodeMalformed(data, offset, "data is shorter than 2 bytes");
			}
			
			// consume and determine type
			byte type = data[offset++];
			
			if (type == BDecoder.BE_INTEGER) {
				// advance until we hit end marker
				int end = offset;
				while (end < data.length && data[end] != BDecoder.BE_END) {
					end++;
				}
				if (end == data.length) {
					throw new BDecodeMalformed(data, offset, "no integer terminator");
				}
				// convert bytes to string and string to integer
				byte[] bytes = Arrays.copyOfRange(data, offset, end);
				// after parsing integer set new offset and advance past terminator
				offset = end + 1;
				
				return new BInteger(parseLong(bytes));
			} else if (type == BDecoder.BE_LIST) {
				BList list = new BList();
				// we're going to use recursion to read elements until we hit end
				while (offset < data.length && data[offset] != BDecoder.BE_END) {
					BValue item = decode();
					list.append(item);
				}
				// advance past terminator
				offset++;
				return list;
			}
			
			throw new BDecodeUnknown(data, offset, type);
		}
	}
	
	public void decode(byte[] data) throws BDecodeError {
		this.root = new BDecoder(data, 0).decode();
	}
}
