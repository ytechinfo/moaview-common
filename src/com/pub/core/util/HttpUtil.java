package com.pub.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.pub.core.entity.DataEntity;

public final class HttpUtil
{
  public static final String CHAR_SET = "UTF-8";
  public static final String PARAM_PAGE_NO = "pageNo";
  public static final String PARAM_COUNT_PER_PAGE = "countPerPage";
  public static final String PAGE_START_KEY = "_startRow";
  public static final String PAGE_END_KEY = "_endRow";

  public static String decode(String s)
    throws UnsupportedEncodingException
  {
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

    entity.put("_ep_locale", localeStr);

    Enumeration e = req.getParameterNames();
    while (e.hasMoreElements()) {
      String key = (String)e.nextElement();

      String[] val = req.getParameterValues(key);

      if (val.length > 1)
        entity.put(key, val);
      else {
        entity.put(key, val[0]);
      }
    }

    if (pageFlag) {
      int pageNo = entity.getInt("pageNo", 1);
      int countPerPage = entity.getInt("countPerPage", 10);

      int first = (pageNo - 1) * countPerPage + 1;
      int last = first + countPerPage - 1;

      entity.put("_startRow", Integer.valueOf(first));
      entity.put("_endRow", Integer.valueOf(last));
    }

    return entity;
  }

  public static String getHeaderInfo(HttpServletRequest req, String headerKey) {
    return req.getHeader(headerKey);
  }

  public static DataEntity getAllHeaderInfo(HttpServletRequest req) {
    DataEntity entity = new DataEntity();

    Enumeration e = req.getHeaderNames();
    while (e.hasMoreElements()) {
      String key = (String)e.nextElement();
      entity.put(key, req.getHeader(key));
    }
    return entity;
  }

  public static String getString(HttpServletRequest req, String name)
  {
    return getString(req, name, "");
  }

  public static String getString(HttpServletRequest req, String name, String initval) {
    String v = req.getParameter(name);

    return v = (v == null) || ("".equals(v)) ? initval : v;
  }

  public static String[] getStringValues(HttpServletRequest req, String name) {
    String[] v = req.getParameterValues(name);

    return v = (v == null) || ("".equals(v)) ? new String[0] : v;
  }

  public static String[] getStringValues(HttpServletRequest req, String name, String[] initval) {
    String[] v = req.getParameterValues(name);

    return v = (v == null) || ("".equals(v)) ? initval : v;
  }

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

  public static String getUuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}