package com.mykola2312.retracker.bencode;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.mykola2312.retracker.bencode.error.BDecodeError;
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
			BType type;
			
			// consume and determine type
			switch (data[offset]) {
			case BE_INTEGER: type = BType.INTEGER; break;
			case BE_LIST: type = BType.LIST; break;
			case BE_DICT: type = BType.DICT; break;
			default: throw new BDecodeUnknown(data, offset, data[offset]);
			}
			offset++;
			
			if (type.equals(BType.INTEGER)) {
				// advance until we hit end marker
				int end = offset;
				while (data[end] != BDecoder.BE_END) {
					end++;
				}
				// convert bytes to string and string to integer
				byte[] bytes = Arrays.copyOfRange(data, offset, end);
				return new BInteger(parseLong(bytes));
			}
			
			return null;
		}
	}
	
	public void decode(byte[] data) throws BDecodeError {
		this.root = new BDecoder(data, 0).decode();
	}
}
