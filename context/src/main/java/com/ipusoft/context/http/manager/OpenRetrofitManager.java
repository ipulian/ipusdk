package com.ipusoft.context.http.manager;

/**
 * author : GWFan
 * time   : 2020/4/19 11:48
 * desc   :
 */

import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.http.HttpConstant;
import com.ipusoft.context.utils.GsonUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenRetrofitManager extends IpuSoftSDK {
    private static final String TAG = "OpenRetrofitManager";

    private static volatile OpenRetrofitManager mInstance;
    private Retrofit mRetrofit;

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
                    .baseUrl(HttpConstant.INNER_BASE_URL)
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
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                GsonUtils.toJson(params));
    }

    @Override
    public void initModule() {
        initRetrofit();
    }
}
