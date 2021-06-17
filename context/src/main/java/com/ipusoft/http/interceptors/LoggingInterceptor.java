package com.ipusoft.http.interceptors;

import android.util.Log;

import com.ipusoft.context.utils.GsonUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author : GWFan
 * time   : 5/28/21 11:09 PM
 * desc   :
 */

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "LoggingInterceptor";

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        //Chain 里包含了request和response
        Request request = chain.request();
        RequestBody body = request.body();
        long t1 = System.nanoTime();
        Log.i(TAG, String.format("发送请求 %s%n", request.url()));
        Log.i(TAG, String.format("请求参数 %s%n", GsonUtils.toJson(request.body())));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //不能直接使用response.body（）.string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，
        // 我们需要创建出一个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.d(TAG, (String.format("接收响应：[%s] %n返回json:%s  %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()
        )));
        return response;
    }
}