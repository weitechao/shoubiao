package com.bracelet.util;

import java.io.UnsupportedEncodingException;

public class RadixUtil {
	public static final String type = "%04x";

	public static String changeRadix(String message) {
		try {
			return String.format(type, message.getBytes("UTF-8").length);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.format(type, message);
	}
	
	public static String changeRadix(Integer length) {
		return String.format(type,length);
	}

}
