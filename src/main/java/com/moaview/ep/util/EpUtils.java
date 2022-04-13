package com.moaview.ep.util;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moaview.ep.exception.EpException;
import com.moaview.ep.vo.DataEntity;
import com.moaview.ep.vo.ResponseResult;
import com.moaview.ep.vo.SearchParameter;

/**
 *
*-----------------------------------------------------------------------------
* @fileName		: EpUtils.java
* @desc		: 공통 util
* @author	: ytkim
*-----------------------------------------------------------------------------
  DATE			AUTHOR			DESCRIPTION
*-----------------------------------------------------------------------------
* 2018. 9. 17. 			ytkim			최초작성

*-----------------------------------------------------------------------------
 */
public class EpUtils {
	
	private EpUtils(){}

	public static String generateUUID (){
		return generateUUID("");
	}

	public static String generateUUID (String prefix){
		return generateUUID(prefix, "");
	}

	public static String generateUUID (String prefix ,String suffix){
		return prefix+UUID.randomUUID().toString().replaceAll("-", "")+suffix;
	}
	/**
	 *
	 * @method  : objectToString
	 * @desc : object 객체 json 스트링으로 변환
	 * @date   : 2015. 7. 03.
	 * @author   : ytkim
	 * @history  :
	 * @param json
	 * @return
	 */
	public static String objectToJsonString(Object json) {
		try {
			return new ObjectMapper().writeValueAsString(json);
		} catch (Exception e) {
			throw new EpException(e.getMessage(), e);
		}
	}

	public static <T> T jsonStringToObject(String jsonString){
		return (T) jsonStringToObject(jsonString, DataEntity.class);
	}

	public static <T> T jsonStringToObject(String jsonString, Class<T> valueType){
		return jsonStringToObject(jsonString, valueType, false);
	}

	public static <T> T jsonStringToObject(String jsonString, Class<T> valueType, boolean ignoreProp){
		return _jsonStringToObject(jsonString, valueType, ignoreProp);
	}

	public static <T> T jsonStringToObject(String jsonString, TypeReference<T> valueType){
		return jsonStringToObject(jsonString, valueType, false);
	}

	public static <T> T jsonStringToObject(String jsonString, TypeReference<T> valueType, boolean ignoreProp){
		return _jsonStringToObject(jsonString, valueType, ignoreProp);
	}

	public static <T> T jsonStringToObject(String jsonString, JavaType valueType){
		return jsonStringToObject(jsonString, valueType, false);
	}

	public static <T> T jsonStringToObject(String jsonString, JavaType valueType, boolean ignoreProp){
		return _jsonStringToObject(jsonString, valueType, ignoreProp);
	}
	/**
	 *
	 * @method  : jsonStringToObject
	 * @desc : jsonString to object  , 프로퍼티 없을때 err여부.
	 * @author   : ytkim
	 * @date   : 2018. 10. 12.
	 * @history  :
	 * @param jsonString
	 * @param valueType
	 * @param ignoreProp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T _jsonStringToObject(String jsonString, Object valueType, boolean ignoreProp){
		if(StringUtils.isBlank(jsonString)) {
			return null;
		}

		try {
			ObjectMapper om = new ObjectMapper();
			if(ignoreProp){
				om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			}else {
				om.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			}
			if(valueType instanceof TypeReference) {
				return om.readValue(jsonString, (TypeReference<T>)valueType);
			}else if(valueType instanceof JavaType) {
				return om.readValue(jsonString, (JavaType)valueType);
			}else {
				return om.readValue(jsonString, (Class<T>)valueType);
			}
		} catch (Exception e) {
			throw new EpException(e.getMessage(), e);
		}
	}
	
	public static ResponseResult getResponseResultItemOne(Object obj) {
		ResponseResult responseResult = new ResponseResult();
		responseResult.setItemOne(obj);
		return responseResult;
	}

	public static ResponseResult getResponseResultItemList(List<?> list) {
		ResponseResult responseResult = new ResponseResult();
		responseResult.setItemList(list);
		return responseResult;
	}
	
	public static ResponseResult getResponseResult(List<?> result, int totalCount , SearchParameter searchParameter) {
		ResponseResult responseResult = new ResponseResult();
		responseResult.setItemList(result);
		responseResult.setPage(PagingUtils.getPageObject(totalCount, searchParameter));
		return responseResult;
	}
}

class DuplicateToArrayJsonNodeDeserializer extends JsonNodeDeserializer {

	private static final long serialVersionUID = 1L;

	@Override
    protected void _handleDuplicateField(JsonParser p, DeserializationContext ctxt,
        JsonNodeFactory nodeFactory,String fieldName, ObjectNode objectNode,
        JsonNode oldValue, JsonNode newValue) throws JsonProcessingException {
        ArrayNode node;
        if(oldValue instanceof ArrayNode){
            node = (ArrayNode) oldValue;
            node.add(newValue);
        } else {
            node = nodeFactory.arrayNode();
            node.add(oldValue);
            node.add(newValue);
        }
        objectNode.set(fieldName, node);
    }
}
