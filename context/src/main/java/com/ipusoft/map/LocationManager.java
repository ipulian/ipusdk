package com.ipusoft.map;

import android.Manifest;
import android.content.Context;

import androidx.core.content.PermissionChecker;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;

/**
 * author : GWFan
 * time   : 6/24/2 9:31 AM
 * desc   :
 */

public class LocationManager {
    private static final String TAG = "LocationManager";

    private static AMapLocation location;

    private static String key;

    private static boolean flag = true;

    private LocationManager() {
    }

    private static class LocationManagerHolder {
        private static final LocationManager INSTANCE = new LocationManager();
    }

    public static LocationManager getInstance(String aMapKey) {
        key = aMapKey;
        return LocationManagerHolder.INSTANCE;
    }

    private static void setLocation(AMapLocation aMapLocation) {
        if (flag) {
            XLog.d("AMapLocation：" + aMapLocation);
            flag = false;
        }
        location = aMapLocation;
//        if (location != null) {
//            String province = location.getProvince();
//            String city = location.getCity();
//            if (StringUtils.isNotEmpty(province) && StringUtils.isNotEmpty(city)) {
//                CommonDataRepo.setLocation(province + "-" + city);
//            }
//        }
    }

    public static AMapLocation getLocation() {
        return location;
    }

    public static String getCity() {
        String city = "";
        if (location != null) {
            // city += location.getProvince();
            city += location.getCity();
        }
        return city;
    }

    public void startLocation(Context context) {
        int result = PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PermissionChecker.PERMISSION_GRANTED) {

            AMapLocationClient.updatePrivacyShow(context, true, true);
            AMapLocationClient.updatePrivacyAgree(context, true);

            try {
                AMapLocationClient mLocationClient = new AMapLocationClient(AppContext.getAppContext());
                AMapLocationClient.setApiKey(key);
                AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
                mLocationOption.setInterval(10 * 60 * 1000);
                mLocationOption.setNeedAddress(true);
                mLocationOption.setOnceLocation(true);
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                /*
                 * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
                 */
                // mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

                mLocationClient.setLocationOption(mLocationOption);
                //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                // mLocationClient.stopLocation();
                mLocationClient.startLocation();
                //设置定位回调监听
                mLocationClient.setLocationListener(LocationManager::setLocation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            XLog.d("没有打开定位权限");
        }
    }
}
