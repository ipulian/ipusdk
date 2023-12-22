package com.ipusoft.ipush;

import android.app.Application;

import androidx.fragment.app.FragmentActivity;

import com.ipusoft.ipush.listener.WebLinkListener;

/**
 * author : GWFan
 * time   : 5/11/21 6:18 PM
 * desc   : 2023-05-18 移除极光推送服务
 */

public class IPushLifecycle {
    private static final String TAG = "IPush";
    private static Application mApp;
    private static WebLinkListener linkListener;

    private IPushLifecycle() {
    }

    public static void initPush(Application mApp, WebLinkListener linkListener) {
        IPushLifecycle.mApp = mApp;
        IPushLifecycle.linkListener = linkListener;
        //JPushInterface.init(mApp);
    }

    public static WebLinkListener getWebLinkListener() {
        return linkListener;
    }

    public static void onResume(FragmentActivity mActivity) {
        // JPushInterface.onResume(mActivity);
    }

    public static void onPause(FragmentActivity mActivity) {
        //  JPushInterface.onPause(mActivity);
    }

    public static String getRegistrationID() {
        // return JPushInterface.getRegistrationID(mApp);
        return "";
    }
}
