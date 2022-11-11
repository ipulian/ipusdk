package com.ipusoft.http.interceptors;

import com.ipusoft.context.AppRuntimeContext;
import com.ipusoft.http.HttpConstant;
import com.ipusoft.utils.StringUtils;

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
            //for (String headerValue : headerValues) {
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
                    baseUrl = AppRuntimeContext.OPEN_BASE_URL;
                } else if (StringUtils.equals(HttpConstant.GATEWAY, headerValue)) {
                    baseUrl = AppRuntimeContext.GATE_WAY_URL;
                } else if (StringUtils.equals(HttpConstant.SEANUM, headerValue)) {
                    baseUrl = "http://api.seanum.com/";
                } else if (StringUtils.equals(HttpConstant.DEV_API, headerValue)) {
                    baseUrl = "https://preapi.51lianlian.cn/";
                }
                /**
                 * 给特定接口单独定义请求的baseUrl(供调试用)
                 */
                for (int i = 0; i < headerValues.size(); i++) {
                    if (headerValues.get(i).startsWith(HttpConstant.TEST)) {
                        baseUrl = headerValues.get(i).split("-")[1];
                    }
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
            // }
        }
        return chain.proceed(request);
    }
}
