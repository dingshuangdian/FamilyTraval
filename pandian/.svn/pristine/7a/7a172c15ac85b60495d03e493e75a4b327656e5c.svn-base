package com.kingtangdata.inventoryassis.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
	
	private static final String KEYVALUE = "QIAOLING";

	public static String md5(String text) {
		if (text == null || text.length() == 0)
			return "";

		byte[] src = text.getBytes();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src);
			byte[] bytes = md.digest(KEYVALUE.getBytes());
			return byte2hex(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String byte2hex(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return "";

		StringBuilder hex = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			int val = ((int) bytes[i]) & 0xff;
			if (val < 16)
				hex.append("0");
			hex.append(Integer.toHexString(val));
		}
		return hex.toString().toUpperCase();
	}
}
