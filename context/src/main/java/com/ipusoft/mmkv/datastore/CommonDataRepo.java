package com.ipusoft.mmkv.datastore;

import android.util.Log;

import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.utils.GsonUtils;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.mmkv.CommonMMKV;
import com.ipusoft.mmkv.constant.StorageConstant;

/**
 * author : GWFan
 * time   : 6/10/21 3:26 PM
 * desc   :
 */

public class CommonDataRepo {
    private static final String TAG = "CommonDataRepo";

    public static void clearAllData() {
        CommonMMKV.clearDataStore();
    }

    public static void setToken(String token) {
        Log.d(TAG, "setToken: ----------" + token);
        if (StringUtils.isNotEmpty(token)) {
            CommonMMKV.set(StorageConstant.TOKEN, token);
        }
    }

    public static String getToken() {
        Log.d(TAG, "getToken: ----");
        return CommonMMKV.getString(StorageConstant.TOKEN);
    }

    public static String getUid() {
        IAuthInfo iAuthInfo = getIAuthInfo();
        String uid = "";
        if (iAuthInfo != null) {
            uid = iAuthInfo.getUid();
        }
        return uid;
    }

    public static void setAuthInfo(AuthInfo authInfo) {
        if (authInfo != null) {
            CommonMMKV.set(StorageConstant.AUTH_INFO, GsonUtils.toJson(authInfo));
        }
    }

    public static AuthInfo getAuthInfo() {
        String json = CommonMMKV.getString(StorageConstant.AUTH_INFO);
        AuthInfo info = null;
        if (StringUtils.isNotEmpty(json)) {
            info = GsonUtils.fromJson(json, AuthInfo.class);
        }
        return info;
    }

    public static void setIAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null && StringUtils.isNotEmpty(iAuthInfo.getToken())
                && StringUtils.isNotEmpty(iAuthInfo.getUid())) {
            CommonMMKV.set(StorageConstant.I_AUTH_INFO, GsonUtils.toJson(iAuthInfo));
        }
    }

    public static IAuthInfo getIAuthInfo() {
        String json = CommonMMKV.getString(StorageConstant.I_AUTH_INFO);
//        Log.d(TAG, "getIAuthInfo: ==========" + json);
        Log.d(TAG, "updateIAuthInfo: --------4" + json);
        IAuthInfo info = null;
        if (StringUtils.isNotEmpty(json)) {
            info = GsonUtils.fromJson(json, IAuthInfo.class);
        }
        return info;
    }

    /**
     * 本地通话方式
     *
     * @param str
     */
    public static void setLocalCallType(String str) {
        CommonMMKV.set(StorageConstant.LOCAL_CALL_TYPE, str);
    }

    public static String getLocalCallType() {
        return CommonMMKV.getString(StorageConstant.LOCAL_CALL_TYPE);
    }

    /**
     * 坐席信息
     *
     * @param seatInfo
     */
    public static void setSeatInfo(SeatInfo seatInfo) {
        String str = "";
        if (seatInfo != null) {
            str = GsonUtils.toJson(seatInfo);
        }
        CommonMMKV.set(StorageConstant.SEAT_INFO, str);
    }

    public static SeatInfo getSeatInfo() {
        String string = CommonMMKV.getString(StorageConstant.SEAT_INFO);
        SeatInfo seatInfo = null;
        try {
            if (StringUtils.isNotEmpty(string)) {
                seatInfo = GsonUtils.fromJson(string, SeatInfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seatInfo;
    }
}
