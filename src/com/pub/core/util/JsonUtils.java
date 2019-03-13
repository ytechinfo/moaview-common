package com.pub.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pub.core.exception.EppltException;

/**
*-----------------------------------------------------------------------------
* @PROJECT	: 포탈 프로젝트
* @NAME		: JsonUtils.java
* @DESC		: json 변환 util 
* @author	: ytkim
*-----------------------------------------------------------------------------
  DATE			AUTHOR			DESCRIPTION
*-----------------------------------------------------------------------------
*2017. 11. 14. 			ytkim			최초작성

*-----------------------------------------------------------------------------
 */
public final class JsonUtils {
	
	/**
	 * single object
	 */
	private JsonUtils (){}
	 
    /**
     * 
     * @Method Name  : stringToJsonMap
     * @Method 설명 : string to map 
     * @작성자   : ytkim
     * @작성일   : 2017. 11. 14. 
     * @변경이력  :
     * @param json
     * @return
     */
	public static Map stringToJsonMap(String json) {
		return (Map) stringToObject(json, HashMap.class);
	}	
	
	/**
	 * 
	 * @Method Name  : stringToJsonClass
	 * @Method 설명 : string to class
	 * @작성자   : ytkim
	 * @작성일   : 2017. 11. 14. 
	 * @변경이력  :
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object stringToJsonClass(String json, Class clazz) {
		return stringToObject(json, clazz);
	}
	
	/**
	 * 
	 * @Method Name  : jsonToString
	 * @Method 설명 : object to string
	 * @작성자   : ytkim
	 * @작성일   : 2017. 11. 14. 
	 * @변경이력  :
	 * @param jsonObject
	 * @return
	 */
	public static String jsonToString(Object jsonObject) {
		return objectToString(jsonObject);
	}
	
	/**
	 * 
	 * @Method Name  : objectToString
	 * @Method 설명 : object to string
	 * @작성자   : ytkim
	 * @작성일   : 2017. 11. 14. 
	 * @변경이력  :
	 * @param json
	 * @return
	 */
	public static String objectToString(Object json) {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(json);
		} catch (JsonMappingException e) {
			throw new EppltException("objectToString JsonMappingException",e); 
		} catch (IOException e) {
			throw new EppltException("objectToString IOException",e); 
		}
	}
	
	/**
	 * 
	 * @Method Name  : stringToObject
	 * @Method 설명 : string to hashmap
	 * @작성자   : ytkim
	 * @작성일   : 2017. 11. 14. 
	 * @변경이력  :
	 * @param jsonString
	 * @return
	 */
	public static <T> T stringToObject(String jsonString) {
		return (T) stringToObject(jsonString, HashMap.class);
	}
	
	/**
	 * 
	 * @Method Name  : stringToObject
	 * @Method 설명 : string to class
	 * @작성자   : ytkim
	 * @작성일   : 2017. 11. 14. 
	 * @변경이력  :
	 * @param jsonString
	 * @param valueType
	 * @return
	 */
	public static <T> T stringToObject(String jsonString, Class<T> valueType) {
		try {
			ObjectMapper om = new ObjectMapper();
			return (T) om.readValue(jsonString, valueType);
		} catch (JsonParseException e) {
			throw new EppltException("objectToString JsonParseException",e); 
		} catch (JsonMappingException e) {
			throw new EppltException("objectToString JsonMappingException",e); 
		} catch (IOException e) {
			throw new EppltException("objectToString IOException",e); 
		}
	}
	
	/**
	 * 
	 * @Method Name  : callbackObjectToString
	 * @Method 설명 : json callback 문자열로 만들기.
	 * @작성자   : ytkim
	 * @작성일   : 2017. 11. 14. 
	 * @변경이력  :
	 * @param callback
	 * @param json
	 * @return
	 */
	public static String callbackObjectToString(String callback, Object json) {
		String reval = objectToString(json);
		if (!"".equals(callback)) {
			reval = callback + "(" + reval + ")";
		}
		return reval;
	}
}
