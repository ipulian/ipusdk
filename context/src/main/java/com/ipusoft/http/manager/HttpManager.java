package com.ipusoft.http.manager;

import com.ipusoft.http.interceptors.BaseUrlInterceptor;
import com.ipusoft.http.interceptors.LoggingInterceptor;
import com.ipusoft.http.interceptors.TimeoutInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

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
    public static OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .dispatcher(getDispatcher())
                .addInterceptor(new TimeoutInterceptor())
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new LoggingInterceptor());

        return builder.build();
    }

    /**
     * 自定义分发器
     *
     * @return
     */
    public static Dispatcher getDispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(64);
        dispatcher.setMaxRequestsPerHost(3);
        return dispatcher;
    }
}
