package com.moaview.ep.constans;

import com.fasterxml.jackson.annotation.JsonValue;

/**
* @fileName		: CodeEnumValue.java
* @desc		: enum code value
* @author	: ytkim
 */
public interface CodeEnumValue {

	@JsonValue
	public int getCode ();

	default String getDesc() {
		return null;
	};
}
