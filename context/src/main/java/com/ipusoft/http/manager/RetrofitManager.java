package com.ipusoft.http.manager;

/**
 * author : GWFan
 * time   : 2020/4/19 11:48
 * desc   :
 */

import com.ipusoft.context.AppRuntimeContext;
import com.ipusoft.context.utils.GsonUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static final String TAG = "RetrofitManager";

    private Retrofit mRetrofit;

    private static class RetrofitManagerHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerHolder.INSTANCE;
    }

    /**
     * 获取Retrofit实例
     *
     * @return
     */
    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            initRetrofit();
        }
        return mRetrofit;
    }

    /**
     * 初始化Retrofit
     */
    public void initRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(HttpManager.getHttpClient())
                    .baseUrl(AppRuntimeContext.BASE_URL)
                    .build();
        }
    }

    /**
     * Map转RequestBody
     *
     * @param params
     * @return
     */
    public RequestBody getRequestBody(Map<String, Object> params) {
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtils.toJson(params));
    }
}
