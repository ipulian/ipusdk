package com.ipusoft.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.IpuSoftSDK;

import java.util.IllegalFormatException;

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

    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(IpuSoftSDK.getAppContext(), id);
    }

    /**
     * 获取String字符串资源
     *
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        return getString(id, (Object[]) null);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        try {
            String text = AppContext.getAppContext().getString(id);
            if (text != null) {
                if (ArrayUtils.isNotEmpty(formatArgs)) {
                    try {
                        text = String.format(text, formatArgs);
                    } catch (IllegalFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            return text;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return String.valueOf(id);
        }
    }

    /**
     * 重绘Drawable背景色
     *
     * @param drawable
     * @param color
     * @return
     */
    public static void tint(Drawable drawable, int color) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        wrappedDrawable.mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
    }
}
