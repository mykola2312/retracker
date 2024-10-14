package com.mykola2312.retracker.bencode.error;

/* this exception should be thrown in cases buffer overrun,
 * missing terminators and other nasty stuff. but it also
 * could signal that there is parsing logic error
 */
public class BDecodeMalformed extends BDecodeError {
	private static final long serialVersionUID = -8134440302174728903L;

	public BDecodeMalformed(byte[] data, int offset, String reason) {
		super(data, offset, reason);
	}
}
