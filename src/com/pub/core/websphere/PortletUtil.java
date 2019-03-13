package com.pub.core.websphere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ClientDataRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.pub.core.entity.DataEntity;
import com.pub.core.util.JsonUtils;

/**
 * 
 * @FileName : PortletUtil.java
 * @프로그램 설명 : portlet application 에서 사용되는 util
 * @Date : Sep 22, 2014
 * @작성자 : ytkim
 * @변경이력 :
 */
public class PortletUtil {
	private static final PortletMode CONFIG_MODE = new PortletMode("config");
	private static final PortletMode EDIT_DEFAULT_MODE = new PortletMode(
			"edit_defaults");

	private PortletUtil() {
	}

	/**
	 * 
	 * @Method Name : getPortletParam
	 * @Method 설명 : 포틀릿으로 들어오는 파라미터 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getPortletParam(PortletRequest req, String name) {
		return getPortletParam(req, name, "");
	}

	/**
	 * 
	 * @Method Name : getPortletParam
	 * @Method 설명 : 포틀릿으로 들어오는 파라미터 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : getPortletAllParam
	 * @Method 설명 : 포틀릿으로 들어온 모든 파라미터 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : getAjaxPortletParam
	 * @Method 설명 : 포틀릿으로 들어온 모든 파라미터 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : getPortalParam
	 * @Method 설명 : 포탈 파라미터 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getPortalParam(PortletRequest req, String name) {
		return getPortletParam(req, name, "");
	}

	/**
	 * 
	 * @Method Name : getPortalParam
	 * @Method 설명 : 포탈 파라미터 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
	 * @param req
	 * @param name
	 * @param initVal
	 * @return
	 */
	public static String getPortalParam(PortletRequest req, String name,
			String initVal) {
		String val = getServletRequest(req).getParameter(name);
		return val != null ? val : initVal;
	}

	/**
	 * 
	 * @Method Name : getHostIp
	 * @Method 설명 : client ip 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
	 * @param req
	 * @return
	 */
	public static String getHostIp(PortletRequest req) {
		HttpServletRequest hsr = getServletRequest(req);
		return hsr.getRemoteAddr();
	}

	/**
	 * 
	 * @Method Name : getServletRequest
	 * @Method 설명 : PortletRequest를 ServletRequest로 변환
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
	 * @param req
	 * @return
	 */
	public static HttpServletRequest getServletRequest(PortletRequest req) {

		if (req instanceof ActionRequest) {
			return ((com.ibm.ws.portletcontainer.core.impl.ActionRequestImpl) req)
					.getHttpServletRequest();
		} else if (req instanceof ResourceRequest) {
			return ((com.ibm.ws.portletcontainer.core.impl.ResourceRequestImpl) req)
					.getHttpServletRequest();
		} else if (req instanceof PortletRequest) {
			return ((com.ibm.ws.portletcontainer.core.impl.PortletRequestImpl) req)
					.getHttpServletRequest();
		} else if (req instanceof RenderRequest) {
			return ((com.ibm.ws.portletcontainer.core.impl.RenderRequestImpl) req)
					.getHttpServletRequest();
		} else {
			return (HttpServletRequest) req;
		}
	}

	/**
	 * 
	 * @Method Name : getJsonMap
	 * @Method 설명 : serveResource -> json data DataEntity
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : parseJson
	 * @Method 설명 : json string -> DataEntity parse
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
	 * @param jsonData
	 * @return
	 */
	public static DataEntity parseJson(String jsonData) {
		return JsonUtils.stringToObject(jsonData, DataEntity.class);
	}

	/**
	 * 
	 * @Method Name : getTitle
	 * @Method 설명 : 포틀릿 타이틀
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : getTitle
	 * @Method 설명 : 포틀릿 타이틀
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : getPreferencesData
	 * @Method 설명 :포틀릿 매개 변수 설정 정보 보기.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : setPreferencesData
	 * @Method 설명 : 포틀릿 매개 변수 설정 정보 저장.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : setPreferencesData
	 * @Method 설명 : 포틀릿 매개 변수 설정 정보 저장.
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : encodeURI
	 * @Method 설명 : char encode
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : encodeURI
	 * @Method 설명 : char encode
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : decodeURI
	 * @Method 설명 : char decode
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
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
	 * @Method Name : decodeURI
	 * @Method 설명 :
	 * @작성일 : Sep 22, 2014
	 * @작성자 : ytkim
	 * @변경이력 :
	 * @param val
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURI(String val, String charset)
			throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(val, charset);
	}
	
	public static boolean isGroup(com.ibm.portal.puma.User currentUser , String groupnm) {
		try {
			List arr = currentUser.getGroups();

			if (arr.size() > 0) {
				Iterator iter = arr.listIterator();

				com.ibm.portal.puma.Group grp = null;
				while (iter.hasNext()) {
					grp = (com.ibm.portal.puma.Group) iter.next();

					if(groupnm.equals(grp.getCommonName())){
						return true; 
					}
				}
			}
			return false;
		} catch (Exception e) {
		}
		return false;
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
	
	public static com.ibm.portal.puma.User getPortalUser(PortletRequest req) {
		return (com.ibm.portal.puma.User)req.getAttribute("com.ibm.portal.puma.request-user");
	}
	
	public static String geUserValue(com.ibm.portal.puma.User currentUser , String key) throws Exception {
		return getUserValue(currentUser, key);
	}
	
	public static String getUserValue(com.ibm.portal.puma.User currentUser , String key) throws Exception {
		Object val = currentUser.get(key); 
		return val != null ? currentUser.get(key).toString():"";
	}	
	
}
