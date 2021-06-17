package com.ipusoft.http.interceptors.base;

import com.ipusoft.context.AppRuntimeContext;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.http.HttpConstant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : GWFan
 * time   : 5/27/21 6:31 PM
 * desc   : 动态替换BaseUrl
 */

public class BaseUrlInterceptor implements Interceptor {
    private static final String TAG = "BaseUrlInterceptor";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl oldHttpUrl = request.url();
        Request.Builder builder = request.newBuilder();
        List<String> headerValues = request.headers(HttpConstant.HOST_NAME);
        if (headerValues.size() > 0) {
            builder.removeHeader(HttpConstant.HOST_NAME);
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl;
            if (StringUtils.isNotEmpty(headerValue)) {
                List<String> pathSegments = request.url().pathSegments();
                String requestPath = "";
                if (pathSegments.size() > 0) {
                    requestPath = StringUtils.join(pathSegments);
                }
                /*
                 * 动态改变运行时环境
                 */
                String baseUrl = "";
                if (StringUtils.equals(HttpConstant.OPEN, headerValue)) {
                    baseUrl = AppRuntimeContext.BASE_URL;
                } else if (StringUtils.equals(HttpConstant.GATEWAY, headerValue)) {
                    baseUrl = AppRuntimeContext.GATE_WAY_URL;
                }
                newBaseUrl = HttpUrl.parse(baseUrl + "/" + requestPath);
            } else {
                newBaseUrl = oldHttpUrl;
            }
            if (newBaseUrl != null) {
                HttpUrl newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme(newBaseUrl.scheme())
                        .host(newBaseUrl.host())
                        .port(newBaseUrl.port())
                        .build();
                return chain.proceed(builder.url(newFullUrl).build());
            }
        }
        return chain.proceed(request);
    }
}
