package com.ipusoft.http.interceptors;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * author : GWFan
 * time   : 5/28/21 11:09 PM
 * desc   : Http请求 日志拦截器
 */

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "LoggingInterceptor";
    private final Charset UTF8 = StandardCharsets.UTF_8;

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        try {
            Request request = chain.request();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            RequestBody requestBody = request.body();


            if (requestBody != null) {
                okhttp3.MediaType mediaType = requestBody.contentType();
                String content = Objects.requireNonNull(response.body()).string();
                Log.d(TAG, "\n");
                Log.d(TAG, "--------------Start-------------------");
                Log.d(TAG, "| " + String.format("发送请求 %s%n", request.url()));
                String method = request.method();
                if ("POST".equals(method)) {
                    StringBuilder sb = new StringBuilder();
                    if (requestBody instanceof FormBody) {
                        try {
                            FormBody formBody = (FormBody) requestBody;
                            for (int i = 0; i < formBody.size(); i++) {
                                sb.append(formBody.encodedName(i)).append("=").append(formBody.encodedValue(i)).append(",");
                            }
                            sb.delete(sb.length() - 1, sb.length());
                            Log.d(TAG, "| 请求参数:{" + sb.toString() + "}");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Log.d(TAG, "intercept: --------------------1");
                    } else {
                        Buffer buffer = new Buffer();
                        requestBody.writeTo(buffer);
                        Charset charset = UTF8;
                        MediaType contentType = requestBody.contentType();
                        //Log.d(TAG, "intercept: ------<" + contentType);
                        if (contentType != null) {
                            charset = contentType.charset(UTF8);
                        }
                        assert charset != null;
                        String body = buffer.readString(charset);
                        Log.d(TAG, "| 请求参数:" + body);
//                    Log.d(TAG, "intercept: ------------21>" + body);
//                    Log.d(TAG, "intercept: ------------2>" + GsonUtils.toJson(requestBody));
                        //Log.d(TAG, "| 请求参数:{" + body + "}");
                    }
                }
                Log.d(TAG, "| 接收响应:" + content);
                Log.d(TAG, "--------------End:" + duration + "ms--------------");
                return response.newBuilder()
                        .body(okhttp3.ResponseBody.create(mediaType, content))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}