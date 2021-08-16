package com.ipusoft.context.platform;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * author : GWFan
 * time   : 7/12/21 2:18 PM
 * desc   :
 */

public class OPPO {

    /**
     * 打开悬浮窗权限
     *
     * @param context
     */
    public static void settingOverlayPermission(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        ComponentName componentName = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
        context.startActivity(intent.setComponent(componentName));
    }
}
