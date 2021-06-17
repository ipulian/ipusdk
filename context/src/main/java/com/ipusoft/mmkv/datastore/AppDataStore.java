package com.ipusoft.mmkv.datastore;

import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.utils.GsonUtils;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.mmkv.AppMMKV;
import com.ipusoft.mmkv.constant.StorageConstant;

/**
 * author : GWFan
 * time   : 6/10/21 3:26 PM
 * desc   :
 */

public class AppDataStore {

    public static void clearAllData() {
        AppMMKV.clearDataStore();
    }

    public static void setToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            AppMMKV.set(StorageConstant.TOKEN, token);
        }
    }

    public static String getToken() {
        return AppMMKV.getString(StorageConstant.TOKEN);
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
            AppMMKV.set(StorageConstant.AUTH_INFO, GsonUtils.toJson(authInfo));
        }
    }

    public static AuthInfo getAuthInfo() {
        String json = AppMMKV.getString(StorageConstant.AUTH_INFO);
        AuthInfo info = null;
        if (StringUtils.isNotEmpty(json)) {
            info = GsonUtils.fromJson(json, AuthInfo.class);
        }
        return info;
    }

    public static void setIAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null && StringUtils.isNotEmpty(iAuthInfo.getToken())
                && StringUtils.isNotEmpty(iAuthInfo.getUid())) {
            AppMMKV.set(StorageConstant.I_AUTH_INFO, GsonUtils.toJson(iAuthInfo));
        }
    }

    public static IAuthInfo getIAuthInfo() {
        String json = AppMMKV.getString(StorageConstant.I_AUTH_INFO);
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
        AppMMKV.set(StorageConstant.LOCAL_CALL_TYPE, str);
    }

    public static String getLocalCallType() {
        return AppMMKV.getString(StorageConstant.LOCAL_CALL_TYPE);
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
        AppMMKV.set(StorageConstant.SEAT_INFO, str);
    }

    public static SeatInfo getSeatInfo() {
        String string = AppMMKV.getString(StorageConstant.SEAT_INFO);
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
