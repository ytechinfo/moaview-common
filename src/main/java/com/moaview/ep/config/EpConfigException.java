package com.moaview.ep.config;

/**
 * @fileName : EpConfigException.java
 * @desc : EpConfig Exception
 * @author : ytkim
 */
public class EpConfigException extends Exception {
	
	private static final long serialVersionUID = 3417096434098863451L;

	public EpConfigException() {
	}

	public EpConfigException(String s) {
		super(s);
	}

	public EpConfigException(String s, Exception exeception) {
		super(s, exeception);
	}
}