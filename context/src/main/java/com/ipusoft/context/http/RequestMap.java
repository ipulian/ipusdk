package com.ipusoft.context.http;

import androidx.annotation.Nullable;

import com.ipusoft.context.AppContext;

import java.util.HashMap;

/**
 * author : GWFan
 * time   : 7/9/20 1:42 PM
 * desc   :Http请求参数
 */

public class RequestMap extends HashMap<String, Object> {

    private static final String TOKEN = "token";

    private RequestMap() {
    }

    /**
     * POST请求的参数Map
     *
     * @return
     */
    public static RequestMap getRequestMap() {
        RequestMap map = new RequestMap();
        map.put(TOKEN, AppContext.getToken());
        return map;
    }

    /**
     * 重写put方法，当value为null时，重写成""
     *
     * @param key
     * @param value
     * @return
     */
    @Nullable
    @Override
    public Object put(String key, Object value) {
        if (value == null) {
            value = "";
        }
        return super.put(key, value);
    }
}
