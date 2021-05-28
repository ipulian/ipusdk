package com.ipusoft.context.http.manager;

/**
 * author : GWFan
 * time   : 2020/4/19 11:48
 * desc   :
 */

import android.util.Log;

import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.http.HttpConstant;
import com.ipusoft.context.http.interceptors.BaseUrlInterceptor;
import com.ipusoft.context.utils.GsonUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : GWFan
 * time   : 2020/4/19 11:48
 * desc   :
 */
public class OpenRetrofitManager extends IpuSoftSDK {
    private static final String TAG = "HttpManager";

    private static volatile OpenRetrofitManager mInstance;
    private Retrofit mRetrofit;

    private boolean printResponseBody = true;

    public static OpenRetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (OpenRetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new OpenRetrofitManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化Retrofit
     */
    public void initRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(getHttpClient())
                    .baseUrl(HttpConstant.INNER_BASE_URL)
                    .build();
        }
    }

    /**
     * 返回Http请求的日志拦截器
     *
     * @return
     */
    protected HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                message -> Log.d("RetrofitLog", "retrofitBack = " + message));
//        loggingInterceptor.setLevel(printResponseBody ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 初始化httpClient
     *
     * @return
     */
    public OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

        builder.addInterceptor(new BaseUrlInterceptor());

        return builder.build();
    }

    /**
     * 获取Retrofit实例
     *
     * @return
     */
    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            initRetrofit();
            //throw new IllegalStateException("Retrofit instance hasn't init!");
        }
        return mRetrofit;
    }

    /**
     * 是否打印HttpBody
     *
     * @param printResponseBody
     * @return
     */
    public Retrofit getRetrofit(boolean printResponseBody) {
        this.printResponseBody = printResponseBody;
        return getRetrofit();
    }

    /**
     * Map转RequestBody
     *
     * @param params
     * @return
     */
    public RequestBody getRequestBody(Map<String, Object> params) {
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                GsonUtils.toJson(params));
    }

    @Override
    public void initModule() {
        initRetrofit();
    }
}