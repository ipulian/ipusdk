package com.ipusoft.context.http.manager;

import android.util.Log;

import com.ipusoft.context.http.interceptors.BaseUrlInterceptor;
import com.ipusoft.context.http.interceptors.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author : GWFan
 * time   : 5/28/21 4:52 PM
 * desc   :
 */

public class HttpManager {

    private static final int CONNECT_TIMEOUT = 8 * 1000;
    private static final int READ_TIMEOUT = 8 * 1000;
    private static final int WRITE_TIMEOUT = 8 * 1000;

    /**
     * 初始化httpClient
     *
     * @return
     */
    public static OkHttpClient getHttpClient(boolean pResBody) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(chain -> {
//                    Request original = chain.request();
//                    Request.Builder requestBuilder = original.newBuilder();
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                })
                .addInterceptor(new LoggingInterceptor())
                //.addInterceptor(getLoggingInterceptor())
                .addInterceptor(new BaseUrlInterceptor());

        return builder.build();
    }

    /**
     * 返回Http请求的日志拦截器
     *
     * @return
     */
    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                message -> Log.d("RetrofitLog", "retrofitBack = " + message));

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
