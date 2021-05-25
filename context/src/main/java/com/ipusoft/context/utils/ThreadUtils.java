package com.ipusoft.context.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * author : GWFan
 * time   : 5/2/21 10:23 PM
 * desc   :
 */

public class ThreadUtils {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            HANDLER.post(runnable);
        }
    }

    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }
}
