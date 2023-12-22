package com.ipusoft.ipush.module

import com.ipusoft.context.bean.base.HttpResponse
import com.ipusoft.ipush.service.PushAPIService
import com.ipusoft.http.manager.RetrofitManager
import com.ipusoft.ipush.bean.PushMessage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * author : GWFan
 * time   : 5/11/21 6:29 PM
 * desc   :
 */

class IPushService {
    companion object {
        /**
         * 更新RegId
         */
        fun updateRegId(params: MutableMap<String, Any>, observer: Observer<HttpResponse>) {
            RetrofitManager.getInstance().retrofit.create(PushAPIService::class.java)
                .updateRegId(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        }

        /**
         * 联动外呼回调
         */
        fun callBack(params: Map<String, Any>, observer: Observer<HttpResponse>) {
            RetrofitManager.getInstance().retrofit.create(PushAPIService::class.java)
                .callBack(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        }

        /**
         * 轮询推送消息
         */
        fun getPushMsg(params: Map<String, Any>, observer: Observer<PushMessage>) {
            RetrofitManager.getInstance().retrofit.create(PushAPIService::class.java)
                .getPushMsg(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        }
    }
}