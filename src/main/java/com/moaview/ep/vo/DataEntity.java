package com.moaview.ep.vo;

public class DataEntity extends AbstractHashMap {
	private static final long serialVersionUID = 1L;

	public synchronized void put(String key, String value) {
		super.put(key, value);
	}
}
