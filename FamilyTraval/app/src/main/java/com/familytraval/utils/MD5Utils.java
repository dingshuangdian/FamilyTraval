package com.familytraval.utils;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;

/**
 * Created by dings on 2016/10/15.
 */


public class MD5Utils {
    public static String getMD5(String data) {
        String hexString = null;
        // 获取数据指纹，算法用MD5
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // new一个字节组，用来读取输入流中的数据
            byte intByte[] = new byte[8910];
            // int 一个count对象，用来记录输入流的读取数量
            int count = 0;
            // 获取输入流
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    data.getBytes());
            // 读取操作，每读取一次，对数据操作digest进行更新操作
            while ((count = bais.read(intByte)) > 0) {
                digest.update(intByte, 0, count);

            }
            // 所有数据读取完毕后,获取读取后的MD5数据
            byte md5[] = digest.digest();
            char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            char datas[] = new char[32];
            // 定义数组下标，用来在data数组中存储转换后的16进制字符
            int index = 0;
            for (byte b : md5) {
                // 获取高4位对应的16进制右侧字符
                datas[index++] = hexChar[b >>> 4 & 0x0f];
                // 获取低4位对应的
                datas[index++] = hexChar[b & 0x0f];
            }
            hexString = new String(datas);
            return hexString;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hexString;

    }


}
