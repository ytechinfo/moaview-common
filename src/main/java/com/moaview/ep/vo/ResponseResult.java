package com.moaview.ep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moaview.ep.constans.CodeEnumValue;
import com.moaview.ep.constans.RequestResultCode;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class ResponseResult implements Serializable{

	private static final long serialVersionUID = 1966357376203287562L;

	/** 상태 코드값*/
	@Builder.Default
	private int status = 200;

	/** 결과 코드값*/
	@Builder.Default
	private CodeEnumValue resultCode = RequestResultCode.SUCCESS;

	/** 결과  메시지 code 다국어를 위한것.*/
	private String messageCode;

	/** 결과  메시지*/
	private String message;

	/** 결과 값 리스트일 경우  */
	private List<Class<?>> list;

	/** 결과 값 하나 일 경우 */
	private Object item;

	/** 페이징 정보 */
	private PagingInfo page;

	/** 추가 데이터   */
	private Map<String, Object> customs;
	
	public ResponseResult(int status, CodeEnumValue resultCode, String messageCode, String message, List<Class<?>> list, Object item, PagingInfo page, Map<String, Object> customs) {
		this.status = status; 
		this.resultCode = resultCode; 
		this.messageCode = messageCode; 
		this.message = message; 
		this.list = list; 
		this.item = item; 
		this.page = page; 
		this.customs = customs; 
	}

	public CodeEnumValue getResultCode() {
		return resultCode;
	}

	public void setResultCode(CodeEnumValue code) {
		this.resultCode = code;
	}

	public List getItems() {
		return list;
	}

	public void setItemList(List items) {
		this.list = items ==null ? new ArrayList():items;
	}

	public <T>T getItem() {
		return (T)item;
	}

	public void setItemOne(Object item) {
		this.item = item;
	}

	public PagingInfo getPage() {
		return page;
	}

	public void setPage(PagingInfo page) {
		this.page = page;
	}

	public void addCustoms(String key , Object obj) {
		if(customs==null){
			customs = new HashMap<String, Object> ();
		}
		customs.put(key, obj);
	}

	public Map<String, Object> getCustoms() {
		return customs;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int statusCode) {
		this.status = statusCode;

		if(this.status != 200){
			this.messageCode = this.messageCode==null? "system.error":this.messageCode;
		}
	}

	/**
	 * 정보를 문자열로 반환한다.
	 *
	 * @return String 검색에 대한 정보
	 */
	@Override
	public String toString() {
		return "JsonReturnEntity  [status=" + status + ",resultCode=" + resultCode + ", message=" + messageCode + ", items=" + list
				+ ", item=" + item + ", page=" + page +"]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
