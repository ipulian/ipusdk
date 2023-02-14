package com.ipusoft.permission;

import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.fragment.app.FragmentActivity;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.view.dialog.OverLayPermissionDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * author : GWFan
 * time   : 4/20/21 4:51 PM
 * desc   : 权限管理类
 */

public class RxPermissionUtils {
    private static final String TAG = "RxPermissionUtils";

    private static final RxPermissionUtils instance = new RxPermissionUtils();

    private String[] permissions;

    /*
     * Must be done during an initialization phase like onCreate
     */
    private RxPermissionUtils requestPermission(String... strings) {
        this.permissions = strings;
        return instance;
    }

    public static boolean hasOverLayPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            try {
                return Settings.canDrawOverlays(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    /**
     * 请求悬浮窗权限
     *
     * @param activity
     */
    public static void requestOverLayPermission(FragmentActivity activity, OverLayPermissionDialog.PermissionCallBack listener) {
        if (!hasOverLayPermission(activity)) {
            OverLayPermissionDialog
                    .getInstance()
                    .setOnOverLayPermissionListener(listener)
                    .show();
        } else {
            listener.invoke(0);
        }
    }

    /**
     * 请求管理分区存储的权限
     *
     * @return
     */
    public static boolean checkManageStoragePermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.R || Environment.isExternalStorageManager();
    }

    /**
     * 检查小米手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkXiaomiRecord() {
        try {
            int key = Settings.System.getInt(AppContext.getAppContext().getContentResolver(), "button_auto_record_call");
            XLog.d(TAG, "Xiaomi key:" + key);
            //0是未开启,1是开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 检查华为手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkHuaweiRecord() {
        try {
            int key = Settings.Secure.getInt(AppContext.getAppContext().getContentResolver(), "enable_record_auto_key");
            //0代表华为自动录音未开启,1代表华为自动录音已开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 检查OPPO手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkOppoRecord() {
        try {
            int key = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                    Settings.Global.getInt(AppContext.getAppContext().getContentResolver(), "oppo_all_call_audio_record") : 0;
            XLog.d(TAG, "Oppo key:" + key);
            //0代表OPPO自动录音未开启,1代表OPPO自动录音已开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
