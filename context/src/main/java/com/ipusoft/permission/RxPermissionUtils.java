package com.ipusoft.permission;

import android.content.Context;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.fragment.app.FragmentActivity;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.view.dialog.OverLayPermissionDialog;
import com.ipusoft.utils.ExceptionUtils;

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

    public static int checkXiaomiRecord() {
        try {
            int key = Settings.System.getInt(AppContext.getAppContext().getContentResolver(), "button_auto_record_call");
            //0是未开启,1是开启
            XLog.d(TAG + "Xiaomi System key：" + key);
            return key;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            XLog.d(TAG + "Xiaomi System key:exception：" + ExceptionUtils.getErrorInfo(e));

            try {
                int key = Settings.Secure.getInt(AppContext.getAppContext().getContentResolver(), "button_auto_record_call");
                //0是未开启,1是开启
                XLog.d(TAG + "Xiaomi Secure key：" + key);
                return key;
            } catch (Settings.SettingNotFoundException e1) {
                e.printStackTrace();
                XLog.d(TAG + "Xiaomi Secure key:exception：" + ExceptionUtils.getErrorInfo(e1));


                try {
                    int key = Settings.Global.getInt(AppContext.getAppContext().getContentResolver(), "button_auto_record_call");
                    //0是未开启,1是开启
                    XLog.d(TAG + "Xiaomi Global key：" + key);
                    return key;
                } catch (Settings.SettingNotFoundException e2) {
                    e.printStackTrace();
                    XLog.d(TAG + "Xiaomi Global key:exception：" + ExceptionUtils.getErrorInfo(e2));

                }

            }
        }
        XLog.d(TAG + "Xiaomi key：-1");

        return -1;
    }


    public static int checkHuaweiRecord() {
        try {
            int key = Settings.Secure.getInt(AppContext.getAppContext().getContentResolver(), "enable_record_auto_key");
            //0代表华为自动录音未开启,1代表华为自动录音已开启
            XLog.d(TAG + "Huawei Secure key：" + key);
            return key;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            XLog.d(TAG + "Huawei Secure key:exception：" + ExceptionUtils.getErrorInfo(e));
            try {
                int key = Settings.System.getInt(AppContext.getAppContext().getContentResolver(), "enable_record_auto_key");
                //0代表华为自动录音未开启,1代表华为自动录音已开启
                XLog.d(TAG + "Huawei System key：" + key);
                return key;
            } catch (Settings.SettingNotFoundException e1) {
                e.printStackTrace();
                XLog.d(TAG + "Huawei System key:exception：" + ExceptionUtils.getErrorInfo(e1));

                try {
                    int key = Settings.Global.getInt(AppContext.getAppContext().getContentResolver(), "enable_record_auto_key");
                    //0代表华为自动录音未开启,1代表华为自动录音已开启
                    XLog.d(TAG + "Huawei Global key：" + key);
                    return key;
                } catch (Settings.SettingNotFoundException e2) {
                    e.printStackTrace();
                    XLog.d(TAG + "Huawei Global key:exception：" + ExceptionUtils.getErrorInfo(e2));
                }
            }
        }
        XLog.d(TAG + "Huawei key：-1");

        return -1;
    }


    public static int checkOppoRecord() {
        try {
            int key = Settings.Global.getInt(AppContext.getAppContext().getContentResolver(), "oppo_all_call_audio_record");
            XLog.d(TAG + "Oppo Global key：" + key);
            //0代表OPPO自动录音未开启,1代表OPPO自动录音已开启
            return key;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            XLog.d(TAG + "Oppo Global key:exception：" + ExceptionUtils.getErrorInfo(e));

            try {
                int key = Settings.System.getInt(AppContext.getAppContext().getContentResolver(), "oppo_all_call_audio_record");
                XLog.d(TAG + "Oppo System key：" + key);
                //0代表OPPO自动录音未开启,1代表OPPO自动录音已开启
                return key;
            } catch (Settings.SettingNotFoundException e1) {
                e.printStackTrace();
                XLog.d(TAG + "Oppo System key:exception：" + ExceptionUtils.getErrorInfo(e1));

                try {
                    int key = Settings.Secure.getInt(AppContext.getAppContext().getContentResolver(), "oppo_all_call_audio_record");
                    XLog.d(TAG + "Oppo Secure key：" + key);
                    //0代表OPPO自动录音未开启,1代表OPPO自动录音已开启
                    return key;
                } catch (Settings.SettingNotFoundException e2) {
                    e.printStackTrace();
                    XLog.d(TAG + "Oppo Secure key:exception：" + ExceptionUtils.getErrorInfo(e2));
                }

            }

        }
        XLog.d(TAG + "Oppo key：-1");

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try (Cursor cursor = AppContext.getAppContext().getContentResolver().query(Settings.Secure.CONTENT_URI, null, null, null)) {
                    String[] columnNames = cursor.getColumnNames();
                    StringBuilder builder = new StringBuilder();
                    while (cursor.moveToNext()) {
                        for (String columnName : columnNames) {
                            String string = cursor.getString(cursor.getColumnIndex(columnName));
                            builder.append(columnName).append(":").append(string).append("\n");
                        }
                    }

                    XLog.d("Settings.Secure.CONTENT_URI----------------->" + builder.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try (Cursor cursor2 = AppContext.getAppContext().getContentResolver().query(Settings.System.CONTENT_URI, null, null, null)) {
                    String[] columnNames2 = cursor2.getColumnNames();
                    StringBuilder builder2 = new StringBuilder();
                    while (cursor2.moveToNext()) {
                        for (String columnName : columnNames2) {
                            String string = cursor2.getString(cursor2.getColumnIndex(columnName));
                            builder2.append(columnName).append(":").append(string).append("\n");
                            // Log.d(TAG, "startTestActivity2: ------>" + columnName + "---->" + string);
                        }
                    }
                    XLog.d("Settings.System.CONTENT_URI----------------->" + builder2.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try (Cursor cursor3 = AppContext.getAppContext().getContentResolver().query(Settings.Global.CONTENT_URI, null, null, null)) {
                    String[] columnNames3 = cursor3.getColumnNames();
                    StringBuilder builder3 = new StringBuilder();
                    while (cursor3.moveToNext()) {
                        for (String columnName : columnNames3) {
                            String string = cursor3.getString(cursor3.getColumnIndex(columnName));
                            builder3.append(columnName).append(":").append(string).append("\n");
                            //   Log.d(TAG, "startTestActivity3: ------>" + columnName + "---->" + string);
                        }
                    }
                    XLog.d("Settings.Global.CONTENT_URI----------------->" + builder3.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    public static int checkViVoRecord() {
        try {
            int key = Settings.Global.getInt(AppContext.getAppContext().getContentResolver(), "call_record_state_global");
            XLog.d(TAG + "Vivo Global key：" + key);
            return key;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            XLog.d(TAG + "Vivo Global key:exception：" + ExceptionUtils.getErrorInfo(e));
            try {
                int key = Settings.System.getInt(AppContext.getAppContext().getContentResolver(), "call_record_state_global");
                XLog.d(TAG + "Vivo System key：" + key);
                return key;
            } catch (Settings.SettingNotFoundException e1) {
                e.printStackTrace();
                XLog.d(TAG + "Vivo System key:exception：" + ExceptionUtils.getErrorInfo(e1));
                try {
                    int key = Settings.Secure.getInt(AppContext.getAppContext().getContentResolver(), "call_record_state_global");
                    XLog.d(TAG + "Vivo Secure key：" + key);
                    return key;
                } catch (Settings.SettingNotFoundException e2) {
                    e.printStackTrace();
                    XLog.d(TAG + "Vivo Secure key:exception：" + ExceptionUtils.getErrorInfo(e2));
                }
            }
        }
        XLog.d(TAG + "Vivo key：-1");
        return -1;
    }
}
