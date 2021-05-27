package com.ipusoft.context.http.impl;

import com.ipusoft.context.http.HttpConstant;
import com.ipusoft.context.http.intercepters.BaseUrlIntercept;

import io.reactivex.rxjava3.annotations.Nullable;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * author : GWFan
 * time   : 5/27/21 2:41 PM
 * desc   : 动态替换BaseUrl
 */

public class BaseUrlInterceptImpl extends BaseUrlIntercept {
    public BaseUrlInterceptImpl(Call.Factory delegate) {
        super(delegate);
    }

    @Override
    protected @Nullable HttpUrl getNewUrl(String baseUrlName, Request request) {
        if (baseUrlName.equals(HttpConstant.OPEN_TAG)) {
            String oldUrl = request.url().toString();
            String newUrl =
                    oldUrl.replace(HttpConstant.INNER_BASE_URL,
                            HttpConstant.OPEN_URL);
            return HttpUrl.get(newUrl);
        }
        return null;
    }
}
