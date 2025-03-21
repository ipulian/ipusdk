package com.ipusoft.http;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.utils.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * author : GWFan
 * time   : 7/9/20 1:42 PM
 * desc   :Http请求参数
 */

@Keep
public class RequestMap extends HashMap<String, Object> {
    private static final String TAG = "RequestMap";
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
        String token = AppContext.getToken();
        if (token.isEmpty()) {
            XLog.e(TAG + "->getRequestMap->Token is empty!");
        }
        map.put(TOKEN, token);
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

    /**
     * 重写putAll方法，当value为null时，重写成""
     *
     * @param m
     * @return
     */
    @Override
    public void putAll(@NonNull Map<? extends String, ?> m) {
        Map<String, Object> mm = new HashMap<>();
        for (Entry<? extends String, ?> entry : m.entrySet()) {
            Object value = entry.getValue();
            if (value == null) {
                value = "";
            }
            mm.put(entry.getKey(), value);
        }
        super.putAll(mm);
    }
}
