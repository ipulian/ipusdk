package com.ipusoft.context.module

import com.ipusoft.context.api.SDKAPIService
import com.ipusoft.context.manager.OpenRetrofitManager
import com.ipusoft.context.bean.IAuthCode
import com.ipusoft.context.bean.IToken
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
            OpenRetrofitManager.getInstance().retrofit.create(SDKAPIService::class.java)
                    .getAuthCode(auth)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        /**
         * 查询Token
         */
        fun getAuthCodeInfo(params: Map<String, Any>, observer: Observer<IToken>) {
            OpenRetrofitManager.getInstance().retrofit.create(SDKAPIService::class.java)
                    .getAuthCodeInfo(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }
}