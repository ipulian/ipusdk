package com.ipusoft.ipush.datastore;

import com.ipusoft.ipush.constant.PushDataStoreConstant;
import com.ipusoft.mmkv.AccountMMKV;

/**
 * author : GWFan
 * time   : 5/11/21 5:11 PM
 * desc   :
 */

public class PushDataRepo {
    /**
     * 推送服务的regId
     *
     * @param regId
     */
    public static void setPushRegId(String regId) {
        AccountMMKV.set(PushDataStoreConstant.REG_ID, regId);
    }

    public static String getPushRegId() {
        return AccountMMKV.getString(PushDataStoreConstant.REG_ID);
    }
}
