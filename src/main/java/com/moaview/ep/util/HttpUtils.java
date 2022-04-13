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
import com.moaview.ep.vo.ClientInfo;
import com.moaview.ep.vo.DataEntity;
import com.moaview.ep.vo.SearchParameter;

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

			entity.put(RequestParamConst.PAGING_START_ROW, first);
			entity.put(RequestParamConst.PAGING_END_ROW, last);
			entity.put(RequestParamConst.PAGING_COUNT_PER_PAGE, countPerPage);
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
	 * @method : getHeaderInfo
	 * @author : ytkim
	 * @date : 2013. 10. 18.
	 * @desc :
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
	 * @method : getString
	 * @author : ytkim
	 * @date : 2013. 10. 18.
	 * @desc :
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
	 * @method : getStringValues
	 * @author : ytkim
	 * @date : 2013. 10. 18.
	 * @desc :
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
	 * @method : getInt
	 * @author : ytkim
	 * @date : 2013. 10. 18.
	 * @desc :
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
     * @method  : createCookie
     * @desc : cookie 생성
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param name
     * @param value
     * @return
     */
	public static Cookie createCookie(String name, String value) throws IOException {
		return new Cookie(name, URLEncoder.encode(value, CHAR_SET));
	}
	
	/**
     * 
     * @method  : createCookie
     * @desc : cookie 생성
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param name
     * @param value
     * @param path
     * @param maxAge
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
     * @method  : createCookie
     * @desc : cookie 생성
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param name
     * @param value
     * @param domain
     * @param path
     * @param maxAge
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
     * @method  : getCookie
     * @desc : cookie 얻기
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param req
     * @param name
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
     * @method  : getValue
     * @desc : cookie value 얻기
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param req HttpServletRequest
     * @param name parameter name
     * @return String cokie value
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
     * @method  : getAllCookieString
     * @desc : all cookie 
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param req
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
     * @method  : getAllReqHeaderString
     * @desc : all request header string
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param req
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
     * @method  : getAllResHeaderString
     * @desc : all response header string
     * @author   : ytkim
     * @date   : 2018. 9. 17. 
     * @history  :
     * @param res
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
	
	public static String getDomain(HttpServletRequest req) {
		
		String connDomain = req.getServerName();
        String nameArr [] = connDomain.split("\\.");
        
        if(nameArr.length > 1 ) {
        	connDomain = nameArr[nameArr.length-2]+"."+nameArr[nameArr.length-1];
        }
		
		return connDomain;
	}
	
	public static String getDomain(HttpServletRequest req, String prefix) {
		return getDomain(req, prefix, false);
	}
	
	public static String getDomain(HttpServletRequest req, String prefix, boolean protocalFlag) {
		
		String connDomain = req.getServerName();
		String nameArr [] = connDomain.split("\\.");
		
		if(nameArr.length > 1 ) {
			connDomain = nameArr[nameArr.length-2]+"."+nameArr[nameArr.length-1];
		}
		
		return (protocalFlag?req.getScheme()+"://":"")+prefix+"."+connDomain;
	}
	
	/**
	 *
	 * @method   : getClientInfo
	 * @desc : client info
	 * @date   : 2019. 9. 21.
	 * @author   : ytkim
	 * @history  :
	 * @param request
	 * @return
	 */
	public static ClientInfo getClientInfo(HttpServletRequest request) {
		String userAgent = "Unknown";
	    String osType = "Unknown";
	    String browser = "Unknown";
	    String deviceType = "pc";

       userAgent = request.getHeader("User-Agent");

       if (userAgent.indexOf("Windows NT") >= 0) {
           osType = "Windows";
       } else if (userAgent.indexOf("Mac OS") >= 0) {
           osType = "Mac";

           if(userAgent.indexOf("iPhone") >= 0) {
               deviceType = "iPhone";
           } else if(userAgent.indexOf("iPad") >= 0) {
               deviceType = "iPad";
           }

       } else if (userAgent.indexOf("X11") >= 0) {
           osType = "Unix";
       } else if (userAgent.indexOf("android") >= 0) {
           osType = "Android";
           deviceType = "Android";
       }

       String userAgentLower = userAgent.toLowerCase();

       if (userAgentLower.contains("msie") || userAgentLower.contains("rv")) {
       	browser= "msie";
       } else if (userAgentLower.contains("safari") && userAgentLower.contains("version")) {
       	browser= "Safari";
       } else if (userAgentLower.contains("opr") || userAgentLower.contains("opera")) {
       	browser= "opera";
       } else if(userAgentLower.contains("edge")){
           browser = "edge";
       } else if (userAgentLower.contains("chrome")) {
       	browser= "chrome";
       } else if ((userAgentLower.indexOf("mozilla/7.0") > -1) || (userAgentLower.indexOf("netscape6") != -1) || (userAgentLower.indexOf(
               "mozilla/4.7") != -1) || (userAgentLower.indexOf("mozilla/4.78") != -1) || (userAgentLower.indexOf(
               "mozilla/4.08") != -1) || (userAgentLower.indexOf("mozilla/3") != -1)) {
           browser = "Netscape";
       } else if (userAgentLower.contains("firefox")) {
       	browser= "firefox";
       } else{
           browser = "UnKnown, More-Info: " + userAgentLower;
       }

       ClientInfo cpi = new ClientInfo();

       cpi.setUserAgent(userAgent);
       cpi.setOsType(osType);
       cpi.setDeviceType(deviceType);
       cpi.setBrowser(browser.toLowerCase());
       cpi.setIp(getClientIp(request));
		return cpi;
	}

	public static boolean isIE(ClientInfo clientPcInfo) {
		return "msie".equalsIgnoreCase(clientPcInfo.getBrowser());
	}

	public static boolean isChrome(ClientInfo clientPcInfo) {
		return "chrome".equalsIgnoreCase(clientPcInfo.getBrowser());
	}

	public static boolean isFirefox(ClientInfo clientPcInfo) {
		return "firefox".equalsIgnoreCase(clientPcInfo.getBrowser());
	}

	public static boolean isSafari(ClientInfo clientPcInfo) {
		return "safari".equalsIgnoreCase(clientPcInfo.getBrowser());
	}

	public static boolean isOpera(ClientInfo clientPcInfo) {
		return "opera".equalsIgnoreCase(clientPcInfo.getBrowser());
	}
	
	/**
	 *
	 * @method  : getClientIp
	 * @desc : ip 정보.
	 * @date   : 2019. 9. 21.
	 * @author   : ytkim
	 * @history  :
	 * @param req
	 * @return
	 */
	public static String getClientIp(HttpServletRequest req) {

		String[] headerKeyArr = {  "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"
				,"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED"
				,"HTTP_FORWARDED_FOR", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_FORWARDED"
		};

		for (String headerKey : headerKeyArr) {
			String ip = req.getHeader(headerKey);

			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}

		return req.getRemoteAddr();
	}
	
	public static String getDownloadFileName(HttpServletRequest req, String downFileName) throws UnsupportedEncodingException {
		ClientInfo clientInfo = getClientInfo(req);

		if (isIE(clientInfo)) {
			downFileName = URLEncoder.encode(downFileName, "UTF-8").replaceAll("\\+", "%20");
		}else if(isFirefox(clientInfo)){
			downFileName = "\""+new String(downFileName.getBytes("UTF-8"), "ISO-8859-1")+"\"";
		}else if(isChrome(clientInfo)){
			downFileName = URLEncoder.encode(downFileName, "UTF-8").replaceAll("\\+", "%20");
		}else if(isSafari(clientInfo)){
			downFileName = "\""+new String(downFileName.getBytes("UTF-8"), "ISO-8859-1")+"\"";
		}else if(isOpera(clientInfo)){
			downFileName = "\""+new String(downFileName.getBytes("UTF-8"), "ISO-8859-1")+"\"";
		}else {
			downFileName = URLEncoder.encode(downFileName, "UTF-8").replaceAll("\\+", "%20");
		}
		
		return downFileName;
	}
}