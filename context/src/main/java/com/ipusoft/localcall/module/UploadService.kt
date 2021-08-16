package com.ipusoft.localcall.module

import com.ipusoft.context.bean.SysRecording
import com.ipusoft.http.manager.RetrofitManager
import com.ipusoft.localcall.UploadFileObserve
import com.ipusoft.localcall.api.LocalCallAPIService
import com.ipusoft.localcall.bean.UploadResponse
import com.ipusoft.localcall.upload.MultipartBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * author : GWFan
 * time   : 4/9/21 11:25 AM
 * desc   :
 */

class UploadService {
    companion object {
        val TAG = "PublicHttpService"

        /**
         * 文件上传
         */
        fun uploadRecordingFile(sysRecording: SysRecording, uploadFileObserve: UploadFileObserve<UploadResponse>) {
            uploadRecordingFiles(listOf(sysRecording), uploadFileObserve)
        }

        /**
         * 多文件上传
         */
        private fun uploadRecordingFiles(sysRecording: List<SysRecording>, uploadFileObserve: UploadFileObserve<UploadResponse>) {
            RetrofitManager.getInstance().retrofit.create(LocalCallAPIService::class.java)
                    .uploadFile(MultipartBuilder.files2MultipartBody(sysRecording, uploadFileObserve))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(uploadFileObserve)
        }
    }
}