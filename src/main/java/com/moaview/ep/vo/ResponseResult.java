package com.moaview.ep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moaview.ep.constans.CodeEnumValue;
import com.moaview.ep.constans.RequestResultCode;

public class ResponseResult implements Serializable {
	private static final long serialVersionUID = 1966357376203287562L;
	/** 상태 코드값*/
	private int status = 200;
	
	/** 결과 코드값*/
	private CodeEnumValue resultCode = RequestResultCode.SUCCESS;
	
	/** 결과  메시지 code 다국어를 위한것.*/
	private String messageCode;
	
	/** 결과  메시지*/
	private String message;
	
	/** 결과 값 리스트일 경우  */
	private List<?> list;
	
	/** 결과 값 하나 일 경우 */
	private Object item;
	
	/** 페이징 정보 */
	private PagingInfo page;
	
	/** 추가 데이터   */
	private Map<String, Object> customs;

	public CodeEnumValue getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(CodeEnumValue code) {
		this.resultCode = code;
	}

	public <T> T getList() {
		return (T) this.list;
	}

	public void setItemList(List<?> items) {
		this.list = (items == null ? new ArrayList() : items);
	}

	public <T> T getItem() {
		return (T) this.item;
	}

	public void setItemOne(Object item) {
		this.item = item;
	}

	public PagingInfo getPage() {
		return this.page;
	}

	public void setPage(PagingInfo page) {
		this.page = page;
	}

	public void addCustoms(String key, Object obj) {
		if (this.customs == null) {
			this.customs = new HashMap();
		}
		this.customs.put(key, obj);
	}

	public void setCustom(HashMap customs) {
		this.customs = customs;
	}

	public Map<String, Object> getCustoms() {
		return this.customs;
	}

	public String getMessageCode() {
		return this.messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int statusCode) {
		this.status = statusCode;
		if (this.status != 200)
			this.messageCode = (this.messageCode == null ? "system.error" : this.messageCode);
	}

	public String toString() {
		return "ResponseResult  [status=" + this.status + ", resultCode=" + this.resultCode + ", message="
				+ this.messageCode + ", items=" + this.list + ", item=" + this.item + ", page=" + this.page + "]";
	}
}