package com.mykola2312.retracker.tracker;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.BitSet;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;

public class InfoHash {
	public static final int HASH_LENGTH = 20;
	
	private byte[] hash = null;
	
	public InfoHash() {
		this.hash = null;
	}
	
	public InfoHash(byte[] hash) {
		this.hash = hash;
	}
	
	public byte[] bytes() {
		return this.hash;
	}
	
	public void fromURLEncoded(String urlParam) throws DecoderException {
		this.hash = URLCodec.decodeUrl(urlParam.getBytes());
	}
	
	public String toURLEncoded() {
		return new String(URLCodec.encodeUrl(new BitSet(), this.hash), StandardCharsets.US_ASCII);
	}
	
	public void fromString(String hashString) throws Exception {
		if (hashString.length() != InfoHash.HASH_LENGTH * 2) {
			throw new Exception(String.format("hash is not 40 characters length: %s, %d", hashString, hashString.length()));
		}
		
		char[] bytePair = new char[2];
		hash = new byte[InfoHash.HASH_LENGTH];
		for (int i = 0; i < InfoHash.HASH_LENGTH; i++) {
			hashString.getChars(i*2, i*2 + 2, bytePair, 0);
			hash[i] = (byte)Short.parseShort(new String(bytePair), 16);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < InfoHash.HASH_LENGTH; i++) {
			out.append(String.format("%02x", hash[i]));
		}
		
		return out.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof InfoHash)) return false;
		InfoHash other = (InfoHash)o;
		return Arrays.equals(this.hash, other.hash);
	}
	
	@Override
	public final int hashCode() {
		return this.hash != null ? Arrays.hashCode(this.hash) : 0;
	}
}
