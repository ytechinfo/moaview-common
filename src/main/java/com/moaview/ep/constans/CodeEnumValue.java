package com.moaview.ep.constans;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * -----------------------------------------------------------------------------
* @fileName		: CodeEnumValue.java
* @desc		: enum code value
* @author	: ytkim
*-----------------------------------------------------------------------------
  DATE			AUTHOR			DESCRIPTION
*-----------------------------------------------------------------------------
*2021. 1. 10. 			ytkim			최초작성

*-----------------------------------------------------------------------------
 */
public interface CodeEnumValue {

	@JsonValue
	public int getCode ();

	default String getDesc() {
		return null;
	};
}
