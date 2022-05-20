package com.moaview.ep.vo;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractHashMap extends HashMap<String,Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param key java.lang.String
	 * @return java.lang.String
	 */
	public String getString(String key){
		
		if(!containsKey(key)) return "";
		
		Object o = super.get(key);
		
		if ( o == null ) {
			return  "";
		}
		
		if( o.getClass().isArray() ) {
			return ( Array.getLength(o) > 0 ?  ( Array.get(o, 0)==null?"":Array.get(o, 0).toString() ) : "" );
		}
		
		return  o.toString();
	}
	/**
	 * 
	 * @param key java.lang.String
	 * @param initVal java.lang.String
	 * @return String
	 */
	public String getString(String key, String initVal){
		String tmpVal =getString(key);
		return "".equals(tmpVal) ? initVal : tmpVal;
	}
	
	/**
	 * 
	 * @param key java.lang.String
	 * @return  String
	 */
	public String getTrimString(String key){
		return trim (getString(key));
	}
	
	/**
	 * 
	 * @param key java.lang.String
	 * @param delim java.lang.String
	 * @return String[]
	 */
	public String[] split(String key, String delim){
		return split(key, delim, new String[0]);
	}
	
	/**
	 * @param key java.lang.String
	 * @param delim  java.lang.String
	 * @return String[] array
	 */
	public String[] split(String key, String delim, String [] defaultValue){
		String a = getString(key);
		
		if("".equals(a)) return defaultValue;
		
		int position=0;
		int delimiterIdx = 0; 
		int strLen = a.length();
		List<String> resultList = new ArrayList<String>();
		
		int len = delim.length();
		while(position <= strLen){
			delimiterIdx = a.indexOf(delim,position);
			if(delimiterIdx > -1){
				resultList.add(a.substring(position, delimiterIdx));
			}else {
				resultList.add(a.substring(position, strLen));
				break;
			}
			position = delimiterIdx+len;
		}
				
		return (String[]) resultList.toArray(new String[]{});
	}

	/**
	 * @param key java.lang.String
	 * @return String[]
	 */
	public String[] getArray(String key){
		return (String[])super.get(key);
	}
	
	/**
	 * @param key java.lang.String
	 * @return boolean
	 */
	public boolean getBoolean(String key){
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean init){
		String value = getString(key);
		
		if ( "".equals(value) )	return init;
		
		try {
			return Boolean.valueOf(value);
		}catch(Exception e){
			return init;
		}
	}
	
	/**
	 * @param key java.lang.String
	 * @return double
	 */
	public double doubleValue(String key){
		String value = getString(key);
	
		if ( "".equals(value) )	return -1;
	
		try {
			return Double.parseDouble(removeComma(value));
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @param key java.lang.String
	 * @return float
	 */
	
	public float floatValue(String key){
		String value = getString(key);
		
		if ( "".equals(value) )	return -1;
	
		try {
			return Float.parseFloat(removeComma(value));
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @param key java.lang.String
	 * @return int
	 */
	public int getInt(String key){
		return getInt(key, -1);
	}
	
	/**
	 * @param key java.lang.String  
	 * @param initVal  
	 * @return int
	 */
	public int getInt(String key, int initVal){
		String value = getString(key);
		
		return !numberChk(value)?initVal: Integer.parseInt(value);
	}
	
	/**
	 * @param key java.lang.String
	 * @return long
	 */
	public long longValue(String key){
		return longValue(key, -1);
	}
	
	public long longValue(String key, long defaultVal){
		String value = getString(key);
		
		if ("".equals(value)) return defaultVal;
		
		try{
			return Long.parseLong(removeComma(value));
		}catch(Exception e){
			return defaultVal;
		}
	}
	
	/**
	 * 
	 * @param str
	 * @return boolean
	 */
	public boolean numberChk(String str){
	    char c;

	    if("".equals(str)) return false;
	    
	    for(int i = 0 ; i < str.length() ; i++){
	        c = str.charAt(i);
	        if(c < 48 || c > 59){
	        	return false;
	        }
	    }
	    return true;
	}
	
	
	/**
	 * @param key java.lang.String
	 * @return Integer
	 */
	public Integer getInteger(String key) {
		return getInt(key);
	}

	/**
	 * @param key java.lang.String
	 * @return Long
	 */
	public Long getLong(String key) {
		return longValue(key);
	}

	/**
	 * @param key java.lang.String
	 * @return Double
	 */
	public Double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}
	
	/**
	 * @param key java.lang.String
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(String key) {

		String value = getString(key);

		if ( "".equals(value) ){
			return new BigDecimal(0);
		}

		try{
			return new BigDecimal(removeComma(value));
		}catch(Exception e){
			return new BigDecimal(0);
		}
	}
	/**
	 * remove "," in string.
	 * @param s java.lang.String
	 * @return String
	 */
	private String removeComma(String s){
		if ( s == null ){
			return null;
		}
		return s.replaceAll("\\,", "");
	}
	
	/**
	 * space remove
	 * "te st"
	 * @param s
	 * @return String "test"
	 */
	
	public String trim(String s) {
		
		if ( s == null ) return ""; 
			
		return s.replaceAll("\\p{Space}", "");
	}
}
