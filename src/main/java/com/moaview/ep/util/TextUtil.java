package com.moaview.ep.util;

public final class TextUtil {
	public static String cutString(String source, String output, int slength) {
		String rtnVal = null;
		if (source != null)
			if (source.length() > slength)
				rtnVal = source.substring(0, slength) + output;
			else
				rtnVal = source;
		return rtnVal;
	}

	public static String cutString(String source, int limitLength) {
		if (limitLength < 4) {
			return source;
		}

		int len = 0;

		StringBuffer rtnValue = new StringBuffer();

		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);

			if ((c < 44032) || (55203 < c))
				len++;
			else {
				len += 2;
			}

			rtnValue.append(c);

			if (len > limitLength - 3) {
				rtnValue.append("...");
				break;
			}
		}

		return rtnValue.toString();
	}

	public static String stringReplace(String str) {
		String match = "[^가-힣xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		return str;
	}

	public static String crossRemove(String str) {
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		str = str.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		str = str.replaceAll("'", "&#39;");
		str = str.replaceAll("eval\\((.*)\\)", "");
		str = str.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
		str = str.replaceAll("script", "");
		str = str.replaceAll(":expression", "");

		return str;
	}

	public static String crossRemoveParamonly(String str) {
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		str = str.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		str = str.replaceAll("'", "&#39;");
		str = str.replaceAll("eval\\((.*)\\)", "");
		str = str.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
		str = str.replaceAll("script", "");
		str = str.replaceAll(":expression", "");

		return str;
	}

	public static String continueSpaceRemove(String str) {
		String match2 = "\\s{2,}";
		str = str.replaceAll(match2, " ");
		return str;
	}

	public static String spaceAllRemove(String str) {
		str.replaceAll("\\p{Space}", "");
		return str;
	}
}