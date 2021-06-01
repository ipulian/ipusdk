package com.ipusoft.context.utils;

import android.content.Context;
import android.os.Vibrator;

import com.ipusoft.context.AppContext;

/**
 * author : GWFan
 * time   : 5/2/21 11:36 PM
 * desc   :
 */

public class VibrateUtils {
    private static Vibrator vibrator;

    private static Vibrator getVibrator() {
        if (vibrator == null) {
            vibrator = (Vibrator) AppContext.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        }
        return vibrator;
    }

    public static void vibrate(final long milliseconds) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(milliseconds);
    }
}
