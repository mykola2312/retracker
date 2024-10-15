package com.mykola2312.retracker.bencode.error;

/* A base type, all bencode related errors are
 * derived from this class for easy try-catching
 */
public class BError extends Exception {
	private static final long serialVersionUID = 1626675512183720021L;

	public BError(String message) {
		super(message);
	}
	
	public BError(String message, Throwable cause) {
		super(message, cause);
	}
}
