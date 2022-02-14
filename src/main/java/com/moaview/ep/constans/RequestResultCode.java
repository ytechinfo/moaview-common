package com.moaview.ep.constans;

/**
 * -----------------------------------------------------------------------------
* @fileName		: RequestResultCode.java
* @desc		: request result code
* @author	: ytkim
*-----------------------------------------------------------------------------
  DATE			AUTHOR			DESCRIPTION
*-----------------------------------------------------------------------------
*2021. 1. 10. 			ytkim			최초작성

*-----------------------------------------------------------------------------
 */
public enum RequestResultCode implements CodeEnumValue {

	SUCCESS(200)
	,BAD_REQUEST(400)
	,LOGIN_INVALID(401)
	,FORBIDDEN(403)
	,NOT_FOUND(404)
	,DUPLICATES(409)
	,CONFLICT(410)
	,PRECONDITION_FAILED(412)
	,DATA_NOT_VALID(422)
	,LOGIN_TIMEOUT(440)

	,ERROR(500)
	,LIMIT_EXCEEDED(509);


	private int val ;

	RequestResultCode(int code){
		this.val= code;
	}

	@Override
	public int getCode() {
		return this.val;
	}

	public static RequestResultCode valueOf(int val){
		for (RequestResultCode code : values()) {
			if(code.val == val ) {
				return code;
			}
		}
		return RequestResultCode.ERROR;
	}

}
