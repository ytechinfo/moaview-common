package com.moaview.ep.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.moaview.ep.vo.DataEntity;

/**
 * 
 * @fileName : PortletUtil.java
 * @desc : portlet application 에서 사용되는 util
 * @author : ytkim
 * @history :
 */
public class PortletUtils {
	private static final PortletMode CONFIG_MODE = new PortletMode("config");
	private static final PortletMode EDIT_DEFAULT_MODE = new PortletMode(
			"edit_defaults");

	private PortletUtils() {
	}

	/**
	 * 
	 * @method : getPortletParam
	 * @desc : 포틀릿으로 들어오는 파라미터 보기.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getPortletParam(PortletRequest req, String name) {
		return getPortletParam(req, name, "");
	}

	/**
	 * 
	 * @method : getPortletParam
	 * @desc : 포틀릿으로 들어오는 파라미터 보기.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param req
	 * @param name
	 * @param initVal
	 * @return
	 */
	public static String getPortletParam(PortletRequest req, String name,
			String initVal) {
		String val = req.getParameter(name);
		return val != null ? val : initVal;
	}

	/**
	 * 
	 * @method : getPortletAllParam
	 * @desc : 포틀릿으로 들어온 모든 파라미터 보기.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param req
	 * @return
	 */
	public static DataEntity getPortletAllParam(PortletRequest req) {
		DataEntity entity = new DataEntity();
		Enumeration e = req.getParameterNames();
		String k, v = "";
		while (e.hasMoreElements()) {
			k = String.valueOf(e.nextElement());
			v = req.getParameter(k);
			entity.put(k, v == null ? "" : v);
		}
		return entity;
	}

	/**
	 * 
	 * @method : getAjaxPortletParam
	 * @desc : 포틀릿으로 들어온 모든 파라미터 보기.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param req
	 * @return
	 */
	public static DataEntity getAjaxPortletParam(PortletRequest req) {
		DataEntity entity = new DataEntity();

		if (req instanceof ResourceRequest) {
			entity.put("ajax_data", getJsonMap((ResourceRequest) req, entity));
		}

		return entity;
	}

	/**
	 * 
	 * @method : getPortalParam
	 * @desc : 포탈 파라미터 보기.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getPortalParam(PortletRequest req, String name) {
		return getPortletParam(req, name, "");
	}

	/**
	 * 
	 * @method : getJsonMap
	 * @desc : serveResource convert json data DataEntity
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param req
	 * @param param
	 * @return
	 */
	private static DataEntity getJsonMap(ResourceRequest req, DataEntity param) {
		DataEntity entity = new DataEntity();
		BufferedReader inReader = null;

		try {
			String tmpStr = param.getString("ajax_data", "");

			if ("POST".equalsIgnoreCase(((ClientDataRequest) req).getMethod())) {
				StringBuffer sb = new StringBuffer();
				inReader = req.getReader();
				String line = null;

				while ((line = inReader.readLine()) != null) {
					sb.append(line);
				}

				tmpStr = sb.toString();

				if (inReader != null)
					inReader.close();
			}

			return parseJson(tmpStr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inReader != null) {
				try {
					inReader.close();
				} catch (Exception e) {
				}
			}
		}

		return entity;
	}

	/**
	 * 
	 * @method : parseJson
	 * @desc : json string convert DataEntity parse
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param jsonData
	 * @return
	 */
	public static DataEntity parseJson(String jsonData) {
		return EpUtils.jsonStringToObject(jsonData, DataEntity.class);
	}

	/**
	 * 
	 * @method : getTitle
	 * @desc : 포틀릿 타이틀
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param portletConfig
	 * @param req
	 * @return
	 */
	public static String getTitle(PortletConfig portletConfig,
			PortletRequest req) {
		return getTitle(portletConfig, req.getLocale());
	}

	/**
	 * 
	 * @method : getTitle
	 * @desc : 포틀릿 타이틀
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param portletConfig
	 * @param locale
	 * @return
	 */
	public static String getTitle(PortletConfig portletConfig, Locale locale) {
		return portletConfig.getResourceBundle(locale).getString(
				"javax.portlet.title");
	}

	/**
	 * 
	 * @method : getPreferencesData
	 * @desc :포틀릿 매개 변수 설정 정보 보기.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param request
	 * @return
	 */
	public static DataEntity getPreferencesData(PortletRequest request) {
		PortletPreferences pref = null;
		DataEntity reMap = new DataEntity();
		if (request != null) {
			pref = request.getPreferences();
			if (pref != null) {
				Enumeration<String> nameEum = pref.getNames();
				String tmpNameStr = "";
				String val = null;
				while (nameEum.hasMoreElements()) {
					tmpNameStr = nameEum.nextElement();
					val = pref.getValue(tmpNameStr, "");
					reMap.put(tmpNameStr, (val != null ? val : ""));
				}
			}
		}
		return reMap;
	}

	/**
	 * 
	 * @method : setPreferencesData
	 * @desc : 포틀릿 매개 변수 설정 정보 저장.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param request
	 * @param saveKey
	 * @return
	 * @throws Exception
	 */
	public static boolean setPreferencesData(PortletRequest request,
			String saveKey) throws Exception {
		return setPreferencesData(request, saveKey,
				getPortletParam(request, saveKey));
	}

	/**
	 * 
	 * @method : setPreferencesData
	 * @desc : 포틀릿 매개 변수 설정 정보 저장.
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param request
	 * @param saveKey
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static boolean setPreferencesData(PortletRequest request,
			String saveKey, String value) throws Exception {
		PortletPreferences pref = null;
		boolean flag = false;

		if (request != null) {
			PortletMode portletMode = request.getPortletMode();
			if (CONFIG_MODE.equals(portletMode)
					|| EDIT_DEFAULT_MODE.equals(portletMode)) {
				pref = request.getPreferences();
				if (pref != null) {
					pref.setValue(saveKey, value);
					pref.store();
					;
					flag = true;
				}
			}
			return flag;
		}
		throw new NullPointerException("request Null");
	}

	/**
	 * 
	 * @method : encodeURI
	 * @desc : char encode
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param val
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeURI(String val)
			throws UnsupportedEncodingException {
		return encodeURI(val, "UTF-8");
	}

	/**
	 * 
	 * @method : encodeURI
	 * @desc : char encode
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param val
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeURI(String val, String charset)
			throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(val, charset);
	}

	/**
	 * char decode
	 * 
	 * @method : decodeURI
	 * @desc : char decode
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param val
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURI(String val)
			throws UnsupportedEncodingException {
		return decodeURI(val, "UTF-8");
	}

	/**
	 * char decode
	 * 
	 * @method : decodeURI
	 * @desc :
	 * @date : Sep 22, 2014
	 * @author : ytkim
	 * @history :
	 * @param val
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURI(String val, String charset)
			throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(val, charset);
	}
	
	public static Map<String, Cookie> getAllCookie(HttpServletRequest request) {
		Map resultMap = new HashMap();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				resultMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return resultMap;
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		return ((Cookie) getAllCookie(request).get(cookieName));
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		if(cookieName==null || "".equals(cookieName)){
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Cookie cookie;
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if(cookieName.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
