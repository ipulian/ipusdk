package com.ipusoft.localcall.repository;

import android.util.Log;

import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.db.AppDBManager;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.localcall.constant.Constant;
import com.ipusoft.localcall.constant.UploadStatus;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author : GWFan
 * time   : 4/28/21 9:13 AM
 * desc   :
 */

public class SysRecordingRepo {
    private static final String TAG = "SysRecordingRepo";

    /**
     * 查询等待上传的记录
     */
    public static void queryWaitingList(int page, IObserver<List<SysRecording>> observer) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(
                ArrayUtils.createList(UploadStatus.WAIT_UPLOAD.getStatus()), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 查询上传成功的记录
     */
    public static void querySucceedList(int page, IObserver<List<SysRecording>> observer) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(
                ArrayUtils.createList(UploadStatus.UPLOAD_SUCCEED.getStatus()), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 查询上传失败的记录
     */
    public static void queryFailedList(int page, IObserver<List<SysRecording>> observer) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(
                ArrayUtils.createList(UploadStatus.UPLOAD_FAILED.getStatus()), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 查询正在上传的记录
     */
    public static void queryUploadingList(int page, IObserver<List<SysRecording>> observer) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(
                ArrayUtils.createList(UploadStatus.UPLOADING.getStatus()), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 根据状态和页数查询记录
     *
     * @param uploadStatus
     * @param page
     * @param observer
     */
    public static void queryByStatusForListPage(List<Integer> uploadStatus, int page,
                                                IObserver<List<SysRecording>> observer) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(uploadStatus, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void queryByStatusForListPage(List<Integer> uploadStatus,
                                                IObserver<List<SysRecording>> observer) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(uploadStatus, Constant.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void queryByStatus(List<Integer> uploadStatus, int retryCount,
                                     long currentTime,
                                     IObserver<List<SysRecording>> observe) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(uploadStatus, retryCount, currentTime, Constant.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observe);
    }

    public static void queryByStatusForMainThread(List<Integer> uploadStatus, int retryCount,
                                                  long currentTime,
                                                  IObserver<List<SysRecording>> observe) {
        AppDBManager.getSysRecordingDao().queryLimitRecordingByStatus(uploadStatus, retryCount, currentTime, Constant.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observe);
    }

    public static void deleteRecording(SysRecording... recording) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter ->
                AppDBManager.getSysRecordingDao().deleteRecording(recording))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public static void deleteAllByStatus(int status) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter ->
                AppDBManager.getSysRecordingDao().deleteRecording(status))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public static void deleteRecording(List<Long> callTimeList) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter ->
                AppDBManager.getSysRecordingDao().deleteRecording(callTimeList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public static void insert(List<SysRecording> list) {
        if (ArrayUtils.isNotEmpty(list)) {
            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                Log.d(TAG, "insert: ------->" + GsonUtils.toJson(list));
                AppDBManager.getSysRecordingDao().insert(list);
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

    public static void updateRecordingStatusByKey(SysRecording recording, int status,
                                                  IObserver<SysRecording> observe) {
        Observable.create((ObservableOnSubscribe<SysRecording>) emitter -> {
            recording.setUploadStatus(status);
            AppDBManager.getSysRecordingDao().updateRecording(recording);
            emitter.onNext(recording);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observe);
    }

    public static void updateRecordingStatusByKey2(SysRecording recording, int status,
                                                   IObserver<SysRecording> observe) {
        Observable.create((ObservableOnSubscribe<SysRecording>) emitter -> {
            recording.setUploadStatus(status);
            AppDBManager.getSysRecordingDao().updateRecording(recording);
            emitter.onNext(recording);
        })
                .subscribe(observe);
    }


    public static void updateRecordingStatusByKey(SysRecording recording, int status) {
        updateRecordingStatusByKey(recording, status, new IObserver<SysRecording>() {
            @Override
            public void onNext(@NotNull @NonNull SysRecording sysRecording) {

            }
        });
    }

    public static void updateRecording(SysRecording recording, IObserver<Boolean> observe) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            AppDBManager.getSysRecordingDao().updateRecording(recording);
            emitter.onNext(true);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observe);
    }

    public static void updateRecordingList
            (List<SysRecording> list, IObserver<Boolean> observe) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            AppDBManager.getSysRecordingDao().updateStatusList(list);
            emitter.onNext(true);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observe);
    }

    public static void deleteOldRecording(List<Integer> statusList, long timestamp, IObserver<
            Boolean> observe) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            AppDBManager.getSysRecordingDao().deleteOldRecording(timestamp, statusList);
            emitter.onNext(true);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observe);
    }
}
