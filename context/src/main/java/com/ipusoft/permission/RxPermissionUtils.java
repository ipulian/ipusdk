package com.ipusoft.permission;

import android.Manifest;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.view.dialog.OverLayPermissionDialog;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ExceptionUtils;
import com.tbruyelle.rxpermissions3.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * author : GWFan
 * time   : 4/20/21 4:51 PM
 * desc   : 权限管理类
 */

public class RxPermissionUtils {
    private static final String TAG = "RxPermissionUtils";
    private static RxPermissions rxPermissions;

    private static final RxPermissionUtils instance = new RxPermissionUtils();

    private String[] permissions;

    /**
     * NOTE: new RxPermissions(this) the this parameter can be a FragmentActivity or a Fragment.
     * If you are using RxPermissions inside of a fragment you should pass the fragment
     * instance(new RxPermissions(this)) as constructor parameter rather than
     * new RxPermissions(fragment.getActivity()) or you could face a java.lang.IllegalStateException:
     * FragmentManager is already executing transactions.
     *
     * @param fragmentActivity
     */
    private static RxPermissionUtils with(FragmentActivity fragmentActivity) {
        rxPermissions = new RxPermissions(fragmentActivity);
        return instance;
    }

    private static RxPermissionUtils with(Fragment fragment) {
        rxPermissions = new RxPermissions(fragment);
        return instance;
    }

    /*
     * Must be done during an initialization phase like onCreate
     */
    private RxPermissionUtils requestPermission(String... strings) {
        this.permissions = strings;
        return instance;
    }

    /**
     * 请求权限的观察者
     *
     * @param observe
     */

    private void observe(IObserver<Boolean> observe) {
        rxPermissions.request(permissions).subscribe(observe);
    }

    /**
     * 判断权限
     *
     * @param observe
     */
    public static void judgeHaveCallPermission(FragmentActivity mActivity, IObserver<Boolean> observe) {
        RxPermissionUtils.requestOverLayPermission(mActivity, aBoolean -> {
            try {
                RxPermissionUtils.with(mActivity).requestPermission(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS
                ).observe(observe);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void requestCallPermission(FragmentActivity activity, IObserver<Boolean> observer) {
        RxPermissionUtils.with(activity).requestPermission(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE)
                .observe(observer);
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

    public static void requestAppPermission(FragmentActivity activity, IObserver<Boolean> observer) {
        try {
            RxPermissionUtils.with(activity).requestPermission(
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.WRITE_CALL_LOG,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.MODIFY_AUDIO_SETTINGS,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.READ_PHONE_STATE)
                    .observe(observer);

            requestLocationPermission2(activity);
            requestLocationPermission3(activity);
        } catch (Exception e) {
            e.printStackTrace();
            XLogger.e(TAG + "->requestAppPermission->" + ExceptionUtils.getErrorInfo(e));
        }
    }

    private static void requestLocationPermission2(FragmentActivity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            RxPermissionUtils.with(activity).requestPermission(
                            Manifest.permission.FOREGROUND_SERVICE)
                    .observe(new IObserver<Boolean>() {
                        @Override
                        public void onNext(@NotNull @NonNull Boolean aBoolean) {
                            requestLocationPermission3(activity);
                        }
                    });
        }
    }

    private static void requestLocationPermission3(FragmentActivity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            RxPermissionUtils.with(activity).requestPermission(
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    .observe(new IObserver<Boolean>() {
                        @Override
                        public void onNext(@NotNull @NonNull Boolean aBoolean) {

                        }
                    });
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
            listener.invoke(true);
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
            int key = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 ?
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
