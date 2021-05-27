package com.ipusoft.context.init;

import com.ipusoft.context.OnSDKLoginListener;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.http.AuthHttp;
import com.ipusoft.context.utils.StringUtils;

import java.security.MessageDigest;
import java.util.Date;

/**
 * author : GWFan
 * time   : 5/27/21 10:36 AM
 * desc   :
 */

public class SDKInit {

    private static final String TAG = "XLibraryApplication";

    private static String authCode;

    private static String sign;

    public static void initSDKToken(IAuthInfo iAuthInfo) {
        initSDKToken(iAuthInfo, status -> {

        });
    }

    public static void initSDKToken(IAuthInfo iAuthInfo, OnSDKLoginListener loginListener) {
        if (iAuthInfo != null) {
            String key = iAuthInfo.getKey(),
                    secret = iAuthInfo.getSecret(),
                    username = iAuthInfo.getUsername();
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(secret) && StringUtils.isNotEmpty(username)) {
                sign = getSign(key, secret, username);
                authCode = getAuth(key, username, sign);
                AuthHttp.checkIdentity(authCode, loginListener);
            }
        }
    }

    public static String getAuthCode() {
        return authCode;
    }

    public static String getSign() {
        return sign;
    }

    private static String getSign(String key, String secret, String username) {
        System.currentTimeMillis();
        return getMD5("dev=SDK&key=" + key + "&ts=" + getSecondTimestamp(new Date())
                + "&username=" + username + secret);
    }

    private static String getAuth(String key, String username, String sign) {
        String str = ("dev=SDK&key=" + key + "&ts=" + getSecondTimestamp(new Date())
                + "&username=" + username + "&sign=" + sign);
        return StringUtils.base64Encode2String(str.getBytes());
    }

    private static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }

    private static String getMD5(String inStr) {
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
}
