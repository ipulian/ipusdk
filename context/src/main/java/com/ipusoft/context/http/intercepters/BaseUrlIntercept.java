package com.ipusoft.context.http.intercepters;

import android.util.Log;

import com.ipusoft.context.http.HttpConstant;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.Nullable;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * author : GWFan
 * time   : 5/27/21 2:15 PM
 * desc   :
 */

public abstract class BaseUrlIntercept implements Call.Factory {
    private static final String TAG = "BaseUrlIntercept";
    private final Call.Factory delegate;

    public BaseUrlIntercept(Call.Factory delegate) {
        this.delegate = delegate;
    }

    @NotNull
    @Override
    public Call newCall(@NotNull Request request) {
        String baseUrlName = request.header(HttpConstant.BASE_URL_NAME);
        if (baseUrlName != null) {
            HttpUrl newHttpUrl = getNewUrl(baseUrlName, request);
            if (newHttpUrl != null) {
                Request newRequest = request.newBuilder().url(newHttpUrl).build();
                return delegate.newCall(newRequest);
            } else {
                Log.d(TAG, "getNewUrl() return null when baseUrlName==" + baseUrlName);
            }
        }
        return delegate.newCall(request);
    }

    @Nullable
    protected abstract HttpUrl getNewUrl(String baseUrlName, Request request);
}
