package com.ipusoft.ipush.service;

import com.ipusoft.context.bean.base.HttpResponse;
import com.ipusoft.ipush.bean.PushMessage;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : GWFan
 * time   : 5/11/21 5:06 PM
 * desc   :
 */

public interface PushAPIService {

    /**
     * 更新RegId
     *
     * @param params
     * @return
     */
    @POST("/updateRegId")
    @FormUrlEncoded
    Observable<HttpResponse> updateRegId(@FieldMap Map<String, Object> params);

    /**
     * 联动外呼回调
     *
     * @param params
     * @return
     */
    @POST("/call/callBack")
    @FormUrlEncoded
    Observable<HttpResponse> callBack(@FieldMap Map<String, Object> params);

    /**
     * 轮询推送消息
     *
     * @param params
     * @return
     */
    @POST("/app/getPushMsg")
    @FormUrlEncoded
    Observable<PushMessage> getPushMsg(@FieldMap Map<String, Object> params);
}
