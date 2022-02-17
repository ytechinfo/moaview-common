package com.moaview.ep.vo;

public class UpperCaseHashMap extends AbstractCaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Override
	public synchronized Object put(String key, Object value) {
		key = key != null? key.toUpperCase() : key; 
		return super.put(key, value);
	}
}
