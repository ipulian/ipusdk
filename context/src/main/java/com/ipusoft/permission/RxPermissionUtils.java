package com.ipusoft.permission;

import android.Manifest;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ipusoft.context.base.IObserver;
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
     * 请求电话相关权限
     *
     * @param observe3
     */
    private void requestCallPermission(IObserver<Boolean> observe3) {
        rxPermissions.request(Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(observe3);
    }

    /**
     * 判断权限
     *
     * @param observe
     */
    public static void judgeHaveCallPermission(FragmentActivity mActivity, IObserver<Boolean> observe) {
        RxPermissionUtils.requestOverLayPermission(mActivity, aBoolean ->
                RxPermissionUtils.with(mActivity).requestCallPermission(observe));
    }

    public static void judgeHaveCallPermission(Fragment mFragment, IObserver<Boolean> observe) {
        RxPermissionUtils.requestOverLayPermission(mFragment.getActivity(), aBoolean ->
                RxPermissionUtils.with(mFragment).requestCallPermission(observe));
    }

    /**
     * 检查是否已经被授予悬浮窗权限
     *
     * @param context
     * @return
     */
//    public static boolean hasOverLayPermission(Activity context) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        return Settings.canDrawOverlays(context);
//    }
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                Log.d(TAG, "hasOverLayPermission: ---------》"+);
//                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//                if (appOpsMgr == null)
//                    return false;
//                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
//                        .getPackageName());
//                return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;

                return Settings.canDrawOverlays(context);

            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }


    /**
     * 请求App相关权限
     *
     * @param activity
     */
    public static void requestAppPermission(FragmentActivity activity) {
        RxPermissionUtils.with(activity).requestPermission(
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.GET_TASKS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE)
                .observe(new IObserver<Boolean>() {
                    @Override
                    public void onNext(@NotNull @NonNull Boolean aBoolean) {
                        Log.d(TAG, "onNext: -------------" + aBoolean);
                    }
                });

        requestLocationPermission2(activity);
        requestLocationPermission3(activity);
    }


    public static void requestAppPermission(FragmentActivity activity, IObserver<Boolean> observer) {
        RxPermissionUtils.with(activity).requestPermission(
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.GET_TASKS,
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
    }

    /**
     * 请求定位权限
     *
     * @param activity
     */
    public static void requestLocationPermission(FragmentActivity activity) {
        RxPermissionUtils.with(activity).requestPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE)
                .observe(new IObserver<Boolean>() {
                    @Override
                    public void onNext(@NotNull @NonNull Boolean aBoolean) {
                        Log.d(TAG, "onNext: ------------>" + aBoolean);
                    }
                });

        requestLocationPermission2(activity);
        requestLocationPermission3(activity);
    }


    public static void requestLocationPermission(Fragment mFragment, IObserver<Boolean> iObserver) {
        RxPermissionUtils.with(mFragment).requestPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE)
                .observe(iObserver);
        requestLocationPermission2(mFragment);
        requestLocationPermission3(mFragment);
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

    private static void requestLocationPermission2(Fragment fragment) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            RxPermissionUtils.with(fragment).requestPermission(
                    Manifest.permission.FOREGROUND_SERVICE)
                    .observe(new IObserver<Boolean>() {
                        @Override
                        public void onNext(@NotNull @NonNull Boolean aBoolean) {
                            requestLocationPermission3(fragment);
                        }
                    });
        }
    }

    private static void requestLocationPermission3(Fragment fragment) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            RxPermissionUtils.with(fragment).requestPermission(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    .observe(new IObserver<Boolean>() {
                        @Override
                        public void onNext(@NotNull @NonNull Boolean aBoolean) {

                        }
                    });
        }
    }

    private static void requestLocationPermission2(FragmentActivity activity, IObserver<Boolean> iObserver) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            RxPermissionUtils.with(activity).requestPermission(
                    Manifest.permission.FOREGROUND_SERVICE)
                    .observe(new IObserver<Boolean>() {
                        @Override
                        public void onNext(@NotNull @NonNull Boolean aBoolean) {
                            requestLocationPermission3(activity, iObserver);
                        }
                    });
        }
    }

    private static void requestLocationPermission3(FragmentActivity activity, IObserver<Boolean> iObserver) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            RxPermissionUtils.with(activity).requestPermission(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    .observe(iObserver);
        }
    }

    /**
     * 请求悬浮窗权限
     *
     * @param activity
     */
    public static void requestOverLayPermission(FragmentActivity activity, OverLayPermissionDialog.PermissionCallBack listener) {
        if (!hasOverLayPermission(activity)) {
            OverLayPermissionDialog.getInstance(activity)
                    .setOnOverLayPermissionListener(listener)
                    .show();
        } else {
            listener.invoke(true);
        }
    }
}
