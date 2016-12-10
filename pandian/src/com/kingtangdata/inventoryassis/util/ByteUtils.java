package com.kingtangdata.inventoryassis.util;

import org.apache.commons.codec.binary.Base64;

/**
 * @title 字节操作工具类 
 * @description 
 * @author 尹泉
 * @date 2009-4-27
 */
public class ByteUtils {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	public static void main(String[] args) {
		Base64 base64 = new Base64();
		String desc = "测试成功!";
		try {
			byte[] encodedBytes = base64.encode(desc.getBytes("GBK"));
			String enStr = new String(encodedBytes);
			System.out.println(enStr);
			byte[] bs = base64.decode(encodedBytes);
			System.out.println(new String(bs, "GBK"));
		} catch (Exception e) {
		}
	}
}
