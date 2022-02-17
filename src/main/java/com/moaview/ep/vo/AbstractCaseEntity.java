package com.moaview.ep.vo;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractCaseEntity extends HashMap<String,Object> {
	private static final long serialVersionUID = 1L;

	public String getString(String key) {
		Object o = super.get(key);
		if (o == null)
			return "";
		if (o.getClass().isArray())
			return Array.getLength(o) > 0 ? Array.get(o, 0).toString() :  "";
		return o.toString();
	}

	public String getString(String key, String initVal) {
		String tmpVal = getString(key);
		return !"".equals(tmpVal) ? tmpVal : initVal;
	}

	public String getTrimString(String key) {
		return trim(getString(key));
	}

	public String[] split(String key, String delim) {
		return split(key, "", delim);
	}

	public String[] split(String key, String initVal, String delim) {
		String a = getString(key, initVal);

		if ("".equals(a)) {
			return new String[0];
		}
		int position = 0;
		int delimiterIdx = 0;
		int strLen = a.length();
		List<String> resultList = new ArrayList();

		int len = delim.length();
		while (position <= strLen) {
			delimiterIdx = a.indexOf(delim, position);
			if (delimiterIdx > -1) {
				resultList.add(a.substring(position, delimiterIdx));
			} else {
				resultList.add(a.substring(position, strLen));
				break;
			}
			position = delimiterIdx + len;
		}

		return (String[]) resultList.toArray(new String[0]);
	}

	public String[] getArray(String key) {
		return (String[]) super.get(key);
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean init) {
		String value = getString(key);
		try {
			if ("".equals(value))
				return init;
			return new Boolean(getString(key)).booleanValue();
		} catch (Exception e) {
		}
		return init;
	}

	public double doubleValue(String key) {
		String value = removeComma(getString(key));

		if ("".equals(value))
			return 0.0D;
		try {
			return Double.valueOf(value).doubleValue();
		} catch (Exception e) {
		}
		return 0.0D;
	}

	public float floatValue(String key) {
		return (float) doubleValue(key);
	}

	public int getInt(String key) {
		return getInt(key, -1);
	}

	public int getInt(String key, int initVal) {
		String value = getString(key);

		return !numberChk(value) ? initVal : new Integer(value).intValue();
	}

	public long longValue(String key) {
		String value = removeComma(getString(key));

		if ("".equals(value)) {
			return 0L;
		}

		long lvalue = 0L;
		try {
			lvalue = Long.valueOf(value).longValue();
		} catch (Exception e) {
			lvalue = 0L;
		}

		return lvalue;
	}

	public boolean numberChk(String str) {
		if ("".equals(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((c < '0') || (c > ';')) {
				return false;
			}
		}
		return true;
	}

	public Integer getInteger(String key) {
		return new Integer(getInt(key));
	}

	public Long getLong(String key) {
		return new Long(longValue(key));
	}

	public Double getDouble(String key) {
		return new Double(doubleValue(key));
	}

	public BigDecimal getBigDecimal(String key) {
		String value = removeComma(getString(key));

		if ("".equals(value)) {
			return new BigDecimal(0);
		}
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
		}
		return new BigDecimal(0);
	}

	private String removeComma(String s) {
		if (s == null) {
			return null;
		}
		return s.replaceAll("\\,", "");
	}

	public String trim(String s) {
		if (s == null) {
			return "";
		}
		return s.replaceAll("\\p{Space}", "");
	}
}
