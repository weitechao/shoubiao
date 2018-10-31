package com.bracelet.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * String字符串工具类.
 * 
 */
public final class StringUtil {

	private static final Log LOG = LogFactory.getLog(StringUtil.class);

	/**
	 * 私有构造方法,将该工具类设为单例模式.
	 */
	private StringUtil() {
	}

	/**
	 * 从json中获取string参数
	 * @param json
	 * @param str
	 * @return
	 */
	public static String getStringFromJson(JSONObject json, String str) {
		return json.containsKey(str) ? json.getString(str) : "";
	}

	public static int getIntegerFromJson(JSONObject json, String str) {
		return json.containsKey(str) ? json.getInteger(str) : 0;
	}

	public static float getFloatFromJson(JSONObject json, String str) {
		return json.containsKey(str) ? json.getFloat(str) : 0;
	}

	public static Date getDateFromJson(JSONObject json, String str) {
		return json.containsKey(str) ? json.getDate(str) : null;//new Date()
	}

	/**
	 * 从json中获取JSONArray
	 * @param json
	 * @param str
	 * @return
	 */
	public static JSONArray getJsonArrayFromJson(JSONObject json, String str) {
		return json.containsKey(str) ? json.getJSONArray(str) : null;
	}

	public static boolean isCorrectNumber(int x) {
		if (x > 0 && x < 3) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 函数功能说明 ： 判断字符串是否为空 . 
	 * 
	 * @参数： @param str
	 * @参数： @return
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return null == str || "".equals(str);
	}

	/**
	 * 函数功能说明 ： 判断对象数组是否为空. 
	 * 
	 * @参数： @param obj
	 * @参数： @return
	 * @return boolean
	 */
	public static boolean isEmpty(Object[] obj) {
		return null == obj || 0 == obj.length;
	}

	/**
	 * 函数功能说明 ： 判断对象是否为空. 
	 * 
	 * @参数： @param obj
	 * @参数： @return
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof String) {
			return ((String) obj).trim().isEmpty();
		}
		return !(obj instanceof Number) ? false : false;
	}

	/**
	 * 函数功能说明 ： 判断集合是否为空. 
	 * 
	 * @参数： @param obj
	 * @参数： @return
	 * @return boolean
	 */
	public static boolean isEmpty(List<?> obj) {
		return null == obj || obj.isEmpty();
	}

	/**
	 * 函数功能说明 ： 判断Map集合是否为空. 
	 * 
	 * @参数： @param obj
	 * @参数： @return
	 * @return boolean
	 */
	public static boolean isEmpty(Map<?, ?> obj) {
		return null == obj || obj.isEmpty();
	}

	/**
	 * 函数功能说明 ： 获得文件名的后缀名. 
	 * 
	 * @参数： @param fileName
	 * @参数： @return
	 * @return String
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 获取去掉横线的长度为32的UUID串.
	 * 
	 * @return uuid.
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取带横线的长度为36的UUID串.
	 * 
	 * @return uuid.
	 */
	public static String get36UUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 计算采用utf-8编码方式时字符串所占字节数
	 * 
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			try {
				// 汉字采用utf-8编码时占3个字节
				size = content.getBytes("utf-8").length;
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}
		}
		return size;
	}

	/**
	 * 函数功能说明 ： 截取字符串拼接in查询参数. 
	 * 
	 * @参数： @param ids
	 * @参数： @return
	 * @return String
	 */
	public static List<String> getInParam(String param) {
		boolean flag = param.contains(",");
		List<String> list = new ArrayList<String>();
		if (flag) {
			list = Arrays.asList(param.split(","));
		} else {
			list.add(param);
		}
		return list;
	}

	/**
	 * 获取key值
	 * @param str
	 * 			HH:mm 字符串
	 * @return
	 */
	public static int getKeyFromString(String str) {
		String st[] = str.split(":");
		String s = st[0];
		return Integer.valueOf(s) * 12;
	}

	/** 
	 * 大陆号码或香港号码均可 
	 */
	public static boolean isPhoneLegal(String str) {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/** 
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
	 * 此方法中前三位格式有： 
	 * 13+任意数 
	 * 15+除4的任意数 
	 * 18+除1和4的任意数 
	 * 17+除9的任意数 
	 * 147 
	 */
	public static boolean isChinaPhoneLegal(String str) {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/** 
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数 
	 */
	public static boolean isHKPhoneLegal(String str) {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
