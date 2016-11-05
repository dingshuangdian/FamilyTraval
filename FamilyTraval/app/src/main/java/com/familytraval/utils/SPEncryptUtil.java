package com.familytraval.utils;

import java.security.MessageDigest;

/**
 * Created by dings on 2016/10/25.
 */

public class SPEncryptUtil {
    private static final String UTF8 = "utf-8";

    public SPEncryptUtil() {
    }

    public static String md5Digest(String src) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(src.getBytes("utf-8"));
        return byte2HexStr(b);
    }

    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < b.length; ++i) {
            String s = Integer.toHexString(b[i] & 255);
            if (s.length() == 1) {
                sb.append("0");
            }

            sb.append(s);
        }

        return sb.toString();
    }
}

