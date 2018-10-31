package com.bracelet;

public class LoginTest {
	public static void main(String[] args) {
		/*
		 * String[] aa = "aaa,bbb\\bccc".split("\\\\");
		 * System.out.println(aa.length); System.out.println(aa[0]);
		 * System.out.println(aa[1]); System.out.println(aa[2]);
		 */

		String bb = "aaa,bbb，bccc";
		String cc = "aaa,bbb,bccc";

		char[] chars = bb.toCharArray();
		for (char aChar : chars) {
			// System.out.println("aChar:-"+isChinesePunctuation(aChar));
			boolean aacb = isChinesePunctuation(aChar);
			if (aacb) {
				System.out.println(true);
			}
		}
		System.out.println("分割线-----------------------------------------");
		for (char aChar : cc.toCharArray()) {
			System.out.println("aChar:-" + isChinesePunctuation(aChar));
		}
	}

	// 根据UnicodeBlock方法判断中文标点符号
	public static boolean isChinesePunctuation(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
				|| ub == Character.UnicodeBlock.VERTICAL_FORMS) {
			return true;
		} else {
			return false;
		}
	}

}
