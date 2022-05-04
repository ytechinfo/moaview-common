package com.moaview.ep.tag;

import javax.servlet.http.HttpServletRequest;

import com.moaview.ep.util.DateUtils;
import com.moaview.ep.util.EpUtils;

/**
 * -----------------------------------------------------------------------------
* @fileName		: VaiFn.java
* @desc		: Vai custom tag function
* @author	: ytkim
*-----------------------------------------------------------------------------
  DATE			AUTHOR			DESCRIPTION
*-----------------------------------------------------------------------------
*2020. 4. 24. 			ytkim			최초작성

*-----------------------------------------------------------------------------
 */
public final class EpTagFn{

	public static String objectToJson(Object json) {
		return EpUtils.objectToJsonString(json);
	}

	public static String currentDate(String foramt) {
		return DateUtils.currentDate(foramt);
	}

	public static long randomVal(Integer val) {
		return java.lang.Math.round(java.lang.Math.random() * val);
	}

	public static String contextPath(HttpServletRequest request) {
		return request.getContextPath();
	}
}
