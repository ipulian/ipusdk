package com.ipusoft.utils;

import java.security.MessageDigest;

/**
 * author : GWFan
 * time   : 5/27/21 5:46 PM
 * desc   :
 */

public class MD5Utils {
    public static String getMD5(String inStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    /**
     * 使用md5的算法进行加密
     */
//    public static String md5(String plainText) {
//        byte[] secretBytes = null;
//        try {
//            secretBytes = MessageDigest.getInstance("md5").digest(
//                    plainText.getBytes());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("没有md5这个算法！");
//        }
//        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
//        // 如果生成数字未满32位，需要前面补0
//        for (int i = 0; i < 32 - md5code.length(); i++) {
//            md5code = "0" + md5code;
//        }
//        return md5code;
//    }

    /**
     * 可逆的的加密解密方法；两次是解密，一次是加密
     *
     * @param inStr
     * @return
     */
    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
}
