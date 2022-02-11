package com.moaview.ep.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moaview.ep.constans.RequestParamConst;
import com.moaview.ep.dto.DataEntity;
import com.moaview.ep.dto.SearchParameter;

public final class HttpUtils {
	public static final String CHAR_SET = "UTF-8";
	public static final String PARAM_PAGE_NO = "pageNo";
	public static final String PARAM_COUNT_PER_PAGE = "countPerPage";
	public static final String PAGE_START_KEY = "_startRow";
	public static final String PAGE_END_KEY = "_endRow";

	public static String decode(String s) throws UnsupportedEncodingException {
		return decode(s, "UTF-8");
	}

	public static String decode(String s, String charset) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, charset);
	}

	public static String encode(String s) throws UnsupportedEncodingException {
		return encode(s, "UTF-8");
	}

	public static String encode(String s, String charset) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, charset);
	}

	public static DataEntity getServletRequestParam(HttpServletRequest req) {
		return getServletRequestParam(req, false);
	}

	public static DataEntity getServletRequestParam(HttpServletRequest req, boolean pageFlag) {
		DataEntity entity = new DataEntity();

		Locale locale = req.getLocale();
		locale = locale == null ? new Locale("ko") : locale;
		String localeStr = locale.toString();
		if (localeStr.length() > 2) {
			localeStr = localeStr.substring(0, 2);
		}

		entity.put(RequestParamConst.REQ_LOCALE, localeStr);

		Enumeration<String> e = req.getParameterNames();
		while (e.hasMoreElements()) {
			String key = e.nextElement();

			String[] val = req.getParameterValues(key);

			if (val.length > 1)
				entity.put(key, val);
			else {
				entity.put(key, val[0]);
			}
		}

		if (pageFlag) {
			int pageNo = entity.getInt(RequestParamConst.PAGE_NO, 1);
			int countPerPage = entity.getInt(RequestParamConst.COUNT_PER_PAGE, 10);

			int first = (pageNo - 1) * countPerPage + 1;
			int last = first + countPerPage - 1;

			entity.put(RequestParamConst.PAGING_START_ROW, Integer.valueOf(first));
			entity.put(RequestParamConst.PAGING_END_ROW, Integer.valueOf(last));
		}

		return entity;
	}
	
	public static SearchParameter getSearchParameter(HttpServletRequest req)  {
		
		String searchVal = req.getParameter(RequestParamConst.SEARCH_VAL);
		
		int no = 1;
		try{ no = Integer.parseInt(req.getParameter(RequestParamConst.PAGE_NO));}catch(Exception e){}
		
		int countPerPage = 10;
		try{ countPerPage = Integer.parseInt(req.getParameter(RequestParamConst.COUNT_PER_PAGE));}catch(Exception e){}
		
		int unitPage = 10;
		try{ unitPage = Integer.parseInt(req.getParameter(RequestParamConst.UNIT_PAGE));}catch(Exception e){}
		
		SearchParameter.Builder searchParam = new SearchParameter.Builder(searchVal,no,countPerPage)
				.setUnitPage(unitPage)
				.setSortCategory(req.getParameter(RequestParamConst.SEARCH_SORT_CATEGORY))
				.setCategory(req.getParameter(RequestParamConst.SEARCH_CATEGORY))
				.setCondition(req.getParameter(RequestParamConst.SEARCH_CONDITION))
				.setSortAscFlag(Boolean.parseBoolean(req.getParameter(RequestParamConst.SEARCH_SORT_ASC_FLAG)));
				
		Enumeration<String> e = req.getParameterNames();
		
		while(e.hasMoreElements()){
			String key = e.nextElement();
			if(
				!key.equals(RequestParamConst.SEARCH_VAL) && !key.equals(RequestParamConst.PAGE_NO)
				&& !key.equals(RequestParamConst.COUNT_PER_PAGE)&& !key.equals(RequestParamConst.UNIT_PAGE)
				&& !key.equals(RequestParamConst.SEARCH_CATEGORY)&& !key.equals(RequestParamConst.SEARCH_SORT_CATEGORY)
				&& !key.equals(RequestParamConst.SEARCH_CONDITION)&& !key.equals(RequestParamConst.SEARCH_SORT_ASC_FLAG)
			)
			searchParam.addCustomParam(key, req.getParameter(key));
		}
		
		return searchParam.build();
	}

	/**
	 * 
	 * @Method Name : getHeaderInfo
	 * @작성자 : ytkim
	 * @작성일 : 2013. 10. 18.
	 * @Method설명 :
	 */
	public static String getHeaderInfo(HttpServletRequest req , String headerKey)  {
		return Collections.list(req.getHeaders(headerKey)).stream().collect(Collectors.joining("  ; "));
	}

	public static DataEntity getAllHeaderInfo(HttpServletRequest req) {
		DataEntity entity = new DataEntity();

		Enumeration<String> e = req.getHeaderNames();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			entity.put(key, req.getHeader(key));
		}
		return entity;
	}

	/**
	 * 
	 * @Method Name : getString
	 * @작성자 : ytkim
	 * @작성일 : 2013. 10. 18.
	 * @Method설명 :
	 */
	public static String getString(HttpServletRequest req, String name) {
		return getString(req, name, "");
	}

	public static String getString(HttpServletRequest req, String name, String initval) {
		String v = req.getParameter(name);

		return v = (v == null) || ("".equals(v)) ? initval : v;
	}

	/**
	 * 
	 * @Method Name : getStringValues
	 * @작성자 : ytkim
	 * @작성일 : 2013. 10. 18.
	 * @Method설명 :
	 */
	public static String[] getStringValues(HttpServletRequest req, String name) {
		String[] v = req.getParameterValues(name);

		return v = (v == null) || ("".equals(v)) ? new String[0] : v;
	}

	public static String[] getStringValues(HttpServletRequest req, String name, String[] initval) {
		String[] v = req.getParameterValues(name);

		return v = (v == null) || ("".equals(v)) ? initval : v;
	}

	/**
	 * 
	 * @Method Name : getInt
	 * @작성자 : ytkim
	 * @작성일 : 2013. 10. 18.
	 * @Method설명 :
	 */
	public static int getInt(HttpServletRequest req, String name) {
		return getInt(req, name, -1);
	}

	public static int getInt(HttpServletRequest req, String name, int initval) {
		String v = getString(req, name);
		try {
			return Integer.parseInt(v);
		} catch (Exception localException) {
		}
		return initval;
	}

	public static boolean isOverIE50(HttpServletRequest req) {
		String user_agent = req.getHeader("user-agent");

		if (user_agent == null) {
			return false;
		}
		int index = user_agent.indexOf("MSIE");
		if (index == -1) {
			return false;
		}
		int version = 0;
		try {
			version = Integer.parseInt(user_agent.substring(index + 5, index + 5 + 1));
		} catch (Exception localException) {
		}
		if (version < 5) {
			return false;
		}
		return true;
	}

	/**
     * 
     * @Method Name  : createCookie
     * @Method 설명 : cookie 생성
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static Cookie createCookie(String name, String value) throws IOException {
		return new Cookie(name, URLEncoder.encode(value, CHAR_SET));
	}
	
	/**
     * 
     * @Method Name  : createCookie
     * @Method 설명 : cookie 생성
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static Cookie createCookie(String name, String value, String path, int maxAge) throws IOException {
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, CHAR_SET));
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	/**
     * 
     * @Method Name  : createCookie
     * @Method 설명 : cookie 생성
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static Cookie createCookie(String name, String value, String domain, String path, int maxAge)
			throws IOException {
		Cookie cookie = new Cookie(name, URLEncoder.encode(value,CHAR_SET));
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	/**
     * 
     * @Method Name  : getCookie
     * @Method 설명 : cookie 얻기
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static Cookie getCookie(HttpServletRequest req, String name) {
		
		Cookie[] cookies =  req.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie.getName() == name) {
					return cookie; 
				}
			}
		}
		return null;
	}
	
	/**
     * 
     * @Method Name  : getValue
     * @Method 설명 : cookie value 얻기
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static String getCookieValue(HttpServletRequest req, String name) throws IOException {
		Cookie cookie = getCookie(req, name);
		if (cookie == null) {
			return null;
		}
		return URLDecoder.decode(cookie.getValue(), CHAR_SET);
	}
	
	/**
     * 
     * @Method Name  : getAllCookieString
     * @Method 설명 : all cookie 
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static String getAllCookieString(HttpServletRequest req) throws IOException {
		StringBuffer sb = new StringBuffer();
		Cookie[] cookies =  req.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				
				sb.append(cookie.getName()).append(" : ").append(cookie.getValue()).append(System.lineSeparator());
			}
		}
		return sb.toString();
	}
	
	/**
     * 
     * @Method Name  : getAllReqHeaderString
     * @Method 설명 : all request header string
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static String getAllReqHeaderString(HttpServletRequest req) throws IOException {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> headerNames =  req.getHeaderNames();
		String name; 
		while(headerNames.hasMoreElements()) {
			name = headerNames.nextElement();
			
			Enumeration<String> values = req.getHeaders(name);
			
			sb.append(name).append(" : ");
			while(values.hasMoreElements()) {
				sb.append(values.nextElement()).append(" ;; ");
			}
			sb.append(System.lineSeparator());
			
		}
		return sb.toString();
	}
	
	/**
     * 
     * @Method Name  : getAllResHeaderString
     * @Method 설명 : all response header string
     * @작성자   : ytkim
     * @작성일   : 2018. 9. 17. 
     * @변경이력  :
     * @param clazz
     * @return
     */
	public static String getAllResHeaderString(HttpServletResponse res) throws IOException {
		StringBuffer sb = new StringBuffer();
		Collection<String> headerNames =  res.getHeaderNames();
		
		for (String name : headerNames) {
			Collection<String> values = res.getHeaders(name);
			
			sb.append(name).append(" : ");
			for(String value : values) {
				sb.append(value).append(" ;; ");
			}
			sb.append(System.lineSeparator());
		}
			
		return sb.toString();
	}
}