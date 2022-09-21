package com.ipusoft.localcall.module

import com.ipusoft.context.bean.SysRecording
import com.ipusoft.http.manager.RetrofitManager
import com.ipusoft.localcall.UploadFileObserve
import com.ipusoft.localcall.api.LocalCallAPIService
import com.ipusoft.localcall.bean.UploadResponse
import com.ipusoft.localcall.upload.UploadRecordMultipartBuilder
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
        fun uploadRecordingFile(
            sysRecording: SysRecording,
            uploadFileObserve: UploadFileObserve<UploadResponse>,
            ossFile: String
        ) {
            uploadRecordingFiles(listOf(sysRecording), uploadFileObserve, ossFile)
        }

        /**
         * 多文件上传
         */
        private fun uploadRecordingFiles(
            sysRecording: List<SysRecording>,
            uploadFileObserve: UploadFileObserve<UploadResponse>,
            ossFile: String
        ) {
            RetrofitManager.getInstance().retrofit.create(LocalCallAPIService::class.java)
                .uploadFile(
                    UploadRecordMultipartBuilder.files2MultipartBody(
                        sysRecording,
                        uploadFileObserve,
                        ossFile
                    )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadFileObserve)
        }
    }
}