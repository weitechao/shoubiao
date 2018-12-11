package com.bracelet.util;

public class RadixUtil {
	public static final String type = "%04x";

	public static String changeRadix(String message) {
		return String.format(type, message.length());
	}
	
	public static String changeRadix(Integer length) {
		return String.format(type,length);
	}

}
