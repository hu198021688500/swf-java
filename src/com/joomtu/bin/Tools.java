package com.joomtu.bin;

import com.joomtu.datrie.store.IntegerArrayList;
import com.joomtu.datrie.store.IntegerList;
import java.io.UnsupportedEncodingException;

public class Tools {

	public static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\\\u" + hexB;
		}
		return unicodeBytes;
	}

	public static String toUnicode(String strText, String code) {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		String fengefu = "";
		if (code.equals("utf-8")) {
			fengefu = "&#x";
		} else if (code.equals("unicode")) {
			fengefu = "\\\\u";
		}
		try {
			strText = new String(strText.getBytes(code), code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int strLen = strText.length();
		for (int i = 0; i < strLen; i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			if (intAsc > 128) {
				strHex = Integer.toHexString(intAsc);
				strRet = strRet + fengefu + strHex;
			} else {
				strRet = strRet + c;
			}
		}
		return strRet;
	}

	public static IntegerList stringToIntegerList(String text) {
		char[] c = text.toCharArray();
		int textLen = c.length;
		IntegerList list = new IntegerArrayList(textLen);
		for (int i = 0; i < textLen; i++) {
			list.add((int) c[i]);
		}
		return list;
	}
}
