package com.ipusoft.ipush;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.BindInfo;
import com.ipusoft.context.bean.Customer;
import com.ipusoft.context.bean.VirtualNumber;
import com.ipusoft.context.cache.AppCacheContext;
import com.ipusoft.context.constant.PhoneState;
import com.ipusoft.context.manager.PhoneManager;
import com.ipusoft.ipush.bean.PushPhoneEvent2App;
import com.ipusoft.ipush.listener.WebLinkListener;
import com.ipusoft.oss.AliYunManager;
import com.ipusoft.permission.RxPermissionUtils;
import com.ipusoft.utils.AppUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.SDKLogUtils;
import com.ipusoft.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 1/8/21 9:11 AM
 * desc   :
 */

public class IPushListener implements WebLinkListener {
    private static final String TAG = "IPushListener";
    private final Application mApp;

    public static final String LOG_NOTIFY = "LOG_NOTIFY";
    public static final String APPROVE_NOTIFY = "APPROVE_NOTIFY";
    public static final String UPLOAD_LOG = "UPLOAD_LOG";
    public static final String APP_VERSION = "APP_VERSION";

    public IPushListener(Application mApp) {
        this.mApp = mApp;
    }

    @Override
    public void onWebLinkListener(PushPhoneEvent2App pushPhoneEvent2App) {
        if (StringUtils.equals(UPLOAD_LOG, pushPhoneEvent2App.getPushType())) {
            XLog.d("收到推送消息，上传App日志");
            String date = pushPhoneEvent2App.getDate();//上传App log日志
            AliYunManager.getInstance().initAliOSS();
            SDKLogUtils.uploadLogInfo("系统自动上传", date, null);
        }
    }
}
