package com.ipusoft.context;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * author : GWFan
 * time   : 5/13/21 10:17 AM
 * desc   :
 */

public class IActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "IActivityLifecycle";

    private static final List<Activity> list = Collections.synchronizedList(new LinkedList<>());

    private static WeakReference<AppCompatActivity> sCurrentActivityWeakRef;

    private int activityCount;//activity的count数
    private boolean isForeground;//是否在前台

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activityCount++;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityCount--;
        if (0 == activityCount) {
            isForeground = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        removeActivity(activity);
    }

    /**
     * Activity Created
     *
     * @param activity
     */
    private static void addActivity(Activity activity) {
        if (activity == null) return;
        list.add(activity);
    }

    /**
     * Activity Destroyed
     *
     * @param activity
     */
    private static void removeActivity(Activity activity) {
        if (activity == null) return;
        list.remove(activity);
    }

    /**
     * 返回当前Activity
     *
     * @return
     */
    @Nullable
    public static AppCompatActivity getCurrentActivity() {
        AppCompatActivity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }


    /**
     * 设置当前Activity
     *
     * @param activity
     */
    private static void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<>((AppCompatActivity) activity);
    }

    /**
     * 返回Activity列表
     *
     * @return
     */
    public static List<Activity> getActivityStack() {
        return list;
    }
}
