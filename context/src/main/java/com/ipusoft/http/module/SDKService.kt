package com.ipusoft.http.module

import com.ipusoft.context.bean.IAuthCode
import com.ipusoft.context.bean.IToken
import com.ipusoft.context.bean.SeatInfo
import com.ipusoft.context.bean.base.HttpResponse
import com.ipusoft.http.api.SDKAPIService
import com.ipusoft.http.manager.RetrofitManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * author : GWFan
 * time   : 5/17/21 3:18 PM
 * desc   :
 */

class SDKService {
    companion object {

        /**
         * 查询AuthCode
         */
        fun getAuthCode(auth: String, observer: Observer<IAuthCode>) {
            RetrofitManager.getInstance().retrofit.create(SDKAPIService::class.java)
                    .getAuthCode(auth)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        /**
         * 查询Token
         */
        fun getAuthCodeInfo(params: Map<String, Any>, observer: Observer<IToken>) {
            RetrofitManager.getInstance().retrofit.create(SDKAPIService::class.java)
                    .getAuthCodeInfo(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        /**
         * 返回坐席呼叫类型
         */
        fun querySeatInfo(params: Map<String, Any>, observer: Observer<SeatInfo>) {
            RetrofitManager.getInstance().retrofit.create(SDKAPIService::class.java)
                    .querySeatInfo(RetrofitManager.getInstance().getRequestBody(params))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        /**
         * 更新外呼方式
         */
        fun updateCallType(params: MutableMap<String, Any>, observer: Observer<HttpResponse>) {
            RetrofitManager.getInstance().retrofit.create(SDKAPIService::class.java)
                    .updateCallType(RetrofitManager.getInstance().getRequestBody(params))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }
}