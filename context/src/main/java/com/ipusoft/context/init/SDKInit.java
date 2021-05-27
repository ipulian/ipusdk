package com.ipusoft.context.init;

import android.util.Log;

import com.ipusoft.context.OnSDKLoginListener;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.http.AuthHttp;
import com.ipusoft.context.utils.MD5Utils;
import com.ipusoft.context.utils.StringUtils;

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

    private static IAuthInfo iAuthInfo;

    public static void initSDKToken(IAuthInfo iAuthInfo) {
        initSDKToken(iAuthInfo, status -> {

        });
    }

    public static void initSDKToken(IAuthInfo iAuthInfo, OnSDKLoginListener loginListener) {
        SDKInit.iAuthInfo = iAuthInfo;
        generateAuthCode();
        AuthHttp.checkIdentity(authCode, loginListener);
    }

    public static String generateAuthCode() {
        if (iAuthInfo != null) {
            String key = iAuthInfo.getKey(),
                    secret = iAuthInfo.getSecret(),
                    username = iAuthInfo.getUsername();
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(secret)
                    && StringUtils.isNotEmpty(username)) {
                sign = getSign(key, secret, username);
                authCode = getAuth(key, username, sign);
            }
        }
        return authCode;
    }

    private static String getSign(String key, String secret, String username) {
        System.currentTimeMillis();
        String md5 = MD5Utils.getMD5("dev=SDK&key=" + key + "&ts=" + getSecondTimestamp(new Date())
                + "&username=" + username + secret);
        Log.d(TAG, "getSign: md5---->" + md5);
        return md5;
    }

    private static String getAuth(String key, String username, String sign) {
        String str = ("dev=SDK&key=" + key + "&ts=" + getSecondTimestamp(new Date())
                + "&username=" + username + "&sign=" + sign);
        String string = StringUtils.base64Encode2String(str.getBytes());
        Log.d(TAG, "getAuth:base64-----> " + string);
        return string;
    }

    private static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }
}
