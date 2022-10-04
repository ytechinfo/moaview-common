package com.moaview.ep.exception;

public class EncryptDecryptException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EncryptDecryptException() {
		super();
	}

	public EncryptDecryptException(String msg) {
		super(msg);
	}

	public EncryptDecryptException(String msg, Throwable t) {
		super(msg, t);
	}

	public EncryptDecryptException(Throwable t) {
		super(t);
	}
}