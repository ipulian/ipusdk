package com.ipusoft.localcall.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.localcall.bean.SIMCallOutBean;
import com.ipusoft.localcall.constant.UploadStatus;
import com.ipusoft.localcall.datastore.SimDataRepo;
import com.ipusoft.localcall.manager.CallLogManager;
import com.ipusoft.localcall.manager.UploadManager;
import com.ipusoft.localcall.repository.SysRecordingRepo;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 4/1/21 2:52 PM
 * desc   : 上传的Service
 */

public class AppCoreService extends Service {
    private static final String TAG = "WHCoreService";

    private static final int CHECK_PERIOD = 5 * 60 * 1000;

    private static final int CHECK_EXPIRE_PERIOD = 24 * 60 * 60 * 1000;

    private static final long EXPIRE_TIME = 3 * 24 * 60 * 60 * 1000;

    private Timer mTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String token = AppContext.getToken();
        if (StringUtils.isNotEmpty(token)) {

            checkMoreRecording();

            new Handler().postDelayed(() -> {
                //每天执行一次，清除3天前的已经上传成功和忽略的数据
                long lastClearOutOfDateRecordingTime = SimDataRepo.getLastClearOutOfDateRecordingTime();
                long l = System.currentTimeMillis();
                if (l - lastClearOutOfDateRecordingTime > CHECK_EXPIRE_PERIOD) {
                    SimDataRepo.setLastClearOutOfDateRecordingTime(l);
                    SysRecordingRepo.deleteOldRecording(
                            ArrayUtils.createList(UploadStatus.UPLOAD_SUCCEED.getStatus(),
                                    UploadStatus.UPLOAD_IGNORE.getStatus()
                            ),
                            System.currentTimeMillis() - EXPIRE_TIME, new IObserver<Boolean>() {
                                @Override
                                public void onNext(@NotNull @NonNull Boolean aBoolean) {

                                }
                            });

                    List<SIMCallOutBean> list = SimDataRepo.getSIMCallOutBean();
                    if (ArrayUtils.isNotEmpty(list)) {
                        for (int i = list.size() - 1; i >= 0; i--) {
                            SIMCallOutBean simCallOutBean = list.get(i);
                            if (System.currentTimeMillis() - simCallOutBean.getTimestamp() >= 3 * 24 * 60 * 60 * 1000) {
                                list.remove(i);
                            }
                        }
                        SimDataRepo.setSIMCallOutBean(list);
                    }
                }
            }, 10 * 1000);
        }
    }

    /**
     * 检查数据库中是否有更多记录
     */
    private void checkMoreRecording() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new CheckMoreDataTask(), 5 * 1000, CHECK_PERIOD);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定期检查系统中是否有更多待处理的数据
     */
    private static class CheckMoreDataTask extends TimerTask {
        @Override
        public void run() {
            CallLogManager.getInstance().queryCallLogAndRecording(new IObserver<Boolean>() {
                @Override
                public void onNext(@NotNull @NonNull Boolean aBoolean) {
                    /*
                     * 检查数据库中是否有更多数据待上传
                     */
                    SysRecordingRepo.queryByStatus(
                            ArrayUtils.createList(
                                    UploadStatus.WAIT_UPLOAD.getStatus(),
                                    UploadStatus.UPLOADING.getStatus(),
                                    UploadStatus.UPLOAD_FAILED.getStatus()),
                            3, System.currentTimeMillis(),
                            new IObserver<List<SysRecording>>() {
                                @Override
                                public void onNext(@NotNull @NonNull List<SysRecording> list) {
                                    if (ArrayUtils.isNotEmpty(list)) {
                                        XLogger.d("数据库中需要上传的任务：" + GsonUtils.toJson(list));
                                        UploadManager.getInstance().addRecordingList2Task(list);
                                    } else {
                                        XLogger.d("数据库中没有需要上传的任务：");
                                    }
                                }
                            });
                }
            });
        }
    }
}