package com.ipusoft.context.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.ipusoft.context.AppContext;

/**
 * author : GWFan
 * time   : 5/2/21 8:53 PM
 * desc   :
 */

public class ResourceUtils {

    private ResourceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(AppContext.getAppContext(), id);
    }
}
