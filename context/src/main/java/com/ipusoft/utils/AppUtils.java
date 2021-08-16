package com.ipusoft.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import com.ipusoft.context.AppContext;

/**
 * author : GWFan
 * time   : 5/2/21 8:44 PM
 * desc   :
 */

public class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * @return the application's package name
     */
    public static String getAppPackageName() {
        return AppContext.getAppContext().getPackageName();
    }

    /**
     * Return the application's name.
     *
     * @return the application's name
     */
    public static String getAppName() {
        return getAppName(AppContext.getAppContext().getPackageName());
    }

    /**
     * Return the application's name.
     *
     * @param packageName The name of the package.
     * @return the application's name
     */
    public static String getAppName(final String packageName) {
        if (StringUtils.isSpace(packageName)) return "";
        try {
            PackageManager pm = AppContext.getAppContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    public static String getAppVersionName() {
        return getAppVersionName(AppContext.getAppContext().getPackageName());
    }

    /**
     * Return the application's version name.
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    public static String getAppVersionName(final String packageName) {
        if (StringUtils.isSpace(packageName)) return "";
        try {
            PackageManager pm = AppContext.getAppContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(AppContext.getAppContext().getPackageName());
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {
        if (StringUtils.isSpace(packageName)) return -1;
        try {
            PackageManager pm = AppContext.getAppContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(AppContext.getAppContext().getPackageName());
    }

    /**
     * Launch the application's details settings.
     *
     * @param pkgName The name of the package.
     */
    public static void launchAppDetailsSettings(final String pkgName) {
        if (StringUtils.isSpace(pkgName)) return;
        Intent intent = getLaunchAppDetailsSettingsIntent(pkgName, true);
        if (!isIntentAvailable(intent)) return;
        AppContext.getAppContext().startActivity(intent);
    }

    public static boolean isIntentAvailable(final Intent intent) {
        return AppContext.getAppContext()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    /**
     * Launch the application's details settings.
     *
     * @param activity    The activity.
     * @param requestCode The requestCode.
     */
    public static void launchAppDetailsSettings(final Activity activity, final int requestCode) {
        launchAppDetailsSettings(activity, requestCode, AppContext.getAppContext().getPackageName());
    }

    public static Intent getLaunchAppDetailsSettingsIntent(final String pkgName, final boolean isNewTask) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + pkgName));
        return getIntent(intent, isNewTask);
    }

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    public static void launchAppDetailsSettings(final Activity activity, final int requestCode, final String pkgName) {
        if (activity == null || StringUtils.isSpace(pkgName)) return;
        Intent intent = getLaunchAppDetailsSettingsIntent(pkgName, false);
        if (!isIntentAvailable(intent)) return;
        activity.startActivityForResult(intent, requestCode);
    }
}
