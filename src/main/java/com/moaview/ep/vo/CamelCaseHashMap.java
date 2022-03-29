package com.moaview.ep.vo;

import org.apache.commons.lang3.StringUtils;

public class CamelCaseHashMap extends AbstractHashMap {
	
	private static final long serialVersionUID = 1L;

	@Override	
	public synchronized Object put(String key, Object value) {
		return super.put(toCamelCase(key.toString()), value);
	}
	
	public static String toCamelCase(String target) {
		StringBuffer buffer = new StringBuffer();
		for (String token : target.toLowerCase().split("_")){
			buffer.append(StringUtils.capitalize(token));
		}
		
		return StringUtils.uncapitalize(buffer.toString());
	}

}
