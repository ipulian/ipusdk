package com.ipusoft.utils;

import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

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

    /**
     * 接口签名样例
     *
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("ts", 1687948090L);
        map.put("phone", "18317893005");
        String json = GsonUtils.toJson(map);
        String sign = MD5Utils.getMD5(json + "336ce5f4b07e8b656c62d53dba16f1d3");
        System.out.println("---->" + sign);
    }
}
