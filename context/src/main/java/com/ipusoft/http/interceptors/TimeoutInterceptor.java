package com.ipusoft.http.interceptors;

import com.ipusoft.http.HttpConstant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : GWFan
 * time   : 5/30/21 11:35 AM
 * desc   : 动态设置接口请求超时时间
 */
public class TimeoutInterceptor implements Interceptor {
    private static final String TAG = "TimeoutInterceptor";

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        List<String> headerValues = request.headers(HttpConstant.TIMEOUT);
        if (headerValues.size() > 0) {
            builder.removeHeader(HttpConstant.TIMEOUT);
            String headerValue = headerValues.get(0);
            return chain.withConnectTimeout(Integer.parseInt(headerValue), TimeUnit.SECONDS)
                    .withReadTimeout(Integer.parseInt(headerValue), TimeUnit.SECONDS)
                    .proceed(request);
        }
        return chain.proceed(request);

    }
}