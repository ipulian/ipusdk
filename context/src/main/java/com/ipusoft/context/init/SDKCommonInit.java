package com.ipusoft.context.init;

import android.util.Log;

import com.ipusoft.context.OnSDKLoginListener;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.http.AuthHttp;
import com.ipusoft.utils.MD5Utils;
import com.ipusoft.utils.StringUtils;

import java.util.Date;

/**
 * author : GWFan
 * time   : 5/27/21 10:36 AM
 * desc   :
 */

public class SDKCommonInit {

    private static final String TAG = "XLibraryApplication";

    private static AuthInfo authInfo;

    public static void initSDKToken(AuthInfo authInfo) {
        initSDKToken(authInfo, status -> {

        });
    }

    public static void initSDKToken(AuthInfo authInfo, OnSDKLoginListener loginListener) {
        SDKCommonInit.authInfo = authInfo;
        String authCode = generateAuthCode();
        AuthHttp.checkIdentity(authCode, loginListener);
    }

    /**
     * 计算AuthCode
     *
     * @return
     */
    public static String generateAuthCode() {
        String authCode = "";
        if (authInfo != null) {
            String key = authInfo.getKey(),
                    secret = authInfo.getSecret(),
                    username = authInfo.getUsername();
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(secret)
                    && StringUtils.isNotEmpty(username)) {
                String sign = getSign(key, secret, username);
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
        String auth = StringUtils.base64Encode2String(str.getBytes());
        Log.d(TAG, "getAuth:base64-----> " + auth);
        return auth;
    }

    private static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }
}
