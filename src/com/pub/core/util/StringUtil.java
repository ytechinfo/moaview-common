package com.pub.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static String paramReplace2(String delim, String str, String key, String replace){
		String reg = delim+key+delim;
		return str.replaceAll(reg, replace);
	}
	
	
	public static String paramReplace(String delim, String str, String key, String replace){
		if(str.indexOf(delim) < 0) return str; 
		
		int position=0, firstIdx = 0 , lastIdx=-1; 
		
		int len = delim.length();
		
		String tmpParam = null, tmpValue=replace;
		StringBuilder sb = new StringBuilder(str);
		
		while( position <= sb.length() ){
			firstIdx = sb.indexOf(delim,position);
			
			if(firstIdx > -1){
				lastIdx = sb.indexOf(delim,firstIdx+len);
				
				if( lastIdx >-1){
					tmpParam =sb.substring(firstIdx+len, lastIdx);
					
					if(key.equals(tmpParam)){
						sb.replace(firstIdx, lastIdx+len , tmpValue);
						return sb.toString();
					}
					
					position = firstIdx+tmpValue.length();
				}else{
					break;
				}
			}else {
				break;
			}
		}
		
		return sb.toString();
	}
	
	public static String[] split(String a,  String delim){
		
		if (("".equals(a)) || (a == null)) return new String[0];
		
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
	
	public static String allTrim(String s){
		if ( s == null ) return ""; 
			
		return s.replaceAll("\\p{Space}", "");
	}
	
	public boolean blankCheck(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ')
				return true;
		}
		return false;
	}
	
	public static boolean chkNullOrBlank(String str){
		if(str == null){
			return false;
		}
		
		if("".equals(str)){
			return false;
		}
		
		if("null".equals(str.toLowerCase())){
			return false;
		}
		
		return true;
	}
	
	public static String nullToString(String str){
		if(str == null){
			return "";
		}
		
		return str; 
	}
	
	public static String nullToString(String str, String initVal){
		if(str == null){
			return initVal;
		}
		
		return str; 
	}
	
	public static String encode(String str, String charset) {
		if (str == null) { // avoid NPE
			return "";
		}

		try {
			return URLEncoder.encode(str, charset).replaceAll("[+]", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String decode(String str, String charset) {
		if (str == null) { 
			return "";
		}
		try {
			return URLDecoder.decode(str, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @Method Name : ltrim
	 * @작성자 : ytkim
	 * @작성일 : 2013. 11. 26.
	 * @Method설명 :
	 */
	public static String ltrim(String read_data) {
		int read_dataLen = read_data.length();
		int blankIdx =-1 ; 
		for (int i = 0; i < read_dataLen; i++) {
			if(!Character.isWhitespace(read_data.charAt(i))){
				blankIdx = i; 
				break ;
			}
		}
		
		return blankIdx !=-1? read_data.substring(blankIdx) :read_data;
			
	}
	
	public static String rtrim(String read_data) {
		int read_dataLen = read_data.length();
		int blankIdx =-1 ; 
		for (int i = 0; i < read_dataLen; i++) {
			if(!Character.isWhitespace(read_data.charAt(i))){
				blankIdx = i ; 
			}
		}
		
		return blankIdx !=-1? read_data.substring(0 , blankIdx+1) :read_data;
	}
	
	public static String allLRtrim(String read_data) {
		return rtrim(ltrim(read_data));
	}
	
	public static String removeSpace(String read_data) {
		return read_data.replaceAll("[\\s]|[\\n]|[\\f]|[\\t]", "");
	}

	public static String allTrim(Object object) {
		if(object== null) return ""; 
		return allTrim(object.toString());
	}
	
	public static String fillChar(String str, int str_length, String fileChar, String FB) {
        
        if (str_length < 1) {
        	return "";
        }
        
        StringBuffer sb = new StringBuffer();
        int strLen = str.length(); 
        if (strLen < str_length) {

            if (FB.equals("B")) { // 뒷쪽에 채우기
                sb.append(str);
                for (int i = (str_length - strLen); i > 0; i--) {
                    sb.append(fileChar);
                }
            } else if (FB.equals("F")) { // 앞쪽에 채우기
                for (int i = (str_length - strLen); i > 0; i--) {
                    sb.append(fileChar);
                }
                sb.append(str);
            }

        } else if (strLen > str_length) {
            sb.append(str.substring(0, str_length));
        } else if (strLen == str_length) {
            sb.append(str.substring(0, str_length));
        }

        return sb.toString();
    }
	
}
