package com.ipusoft.context;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.bean.HttpObserver;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.http.RequestMap;
import com.ipusoft.ipush.PushEventHandler;
import com.ipusoft.ipush.bean.PushMessage;
import com.ipusoft.ipush.http.PushHttp;
import com.ipusoft.ipush.module.IPushService;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author : GWFan
 * time   : 8/5/21 5:37 PM
 * desc   :
 */
public class PushCoreService extends BaseLifeCycleService {
    private static final String TAG = "PushCoreService";
    private volatile Timer mTimer;

    @Override
    protected void onICreate() {

    }

    @Override
    protected void bindLiveData() {

    }

    @Override
    protected void onIStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onIStartCommand: ----------------1");

        startGetPushMessageTask();

    }

    private synchronized void startGetPushMessageTask() {
        if (mTimer == null) {
            Log.d(TAG, "onIStartCommand: ----------------2");
            synchronized (PushCoreService.class) {
                if (mTimer == null) {
                    Log.d(TAG, "onIStartCommand: ----------------3");
                    mTimer = new Timer();
                    mTimer.schedule(new GetPushMessageTask(), 3000, 56 * 1000);
                }
            }
        }
    }

    private static class GetPushMessageTask extends TimerTask {
        @Override
        public void run() {
            //String pushType = AccountRepo.getPushType();
            XLog.d("-----------------定时心跳-----------------");
            //if (StringUtils.isNotEmpty(pushType) && StringUtils.equals("1", pushType)) {
            String token = AppContext.getToken();
            Log.d(TAG, "run: -------->" + token);
            if (StringUtils.isNotEmpty(token)) {
                PushHttp.getPushMsg();
            } else {
                XLog.d("--------------------------token不存在");
            }
//            } else {
//                try {
//                    if (mTimer != null) {
//                        mTimer.cancel();
//                        mTimer = null;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    XLog.e(TAG + "->GetPushMessage->" + ExceptionUtils.getErrorInfo(e));
//                }
//            }
        }
    }

    @Override
    protected void onIDestroy() {
        try {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLog.e(TAG + "->GetPushMessage->" + ExceptionUtils.getErrorInfo(e));
        }
    }
}
