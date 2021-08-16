package com.ipusoft.context.constant;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * author : GWFan
 * time   : 7/19/21 10:52 AM
 * desc   :
 */

public class DateTimePattern {

    public static final String DATE = "yyyy-MM-dd";

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm";

    public static final String DATE_TIME_WITH_SECOND = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<Map<String, SimpleDateFormat>> SDF_THREAD_LOCAL
            = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>();
        }
    };

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getDateFormat(String pattern) {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(pattern);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern);
            sdfMap.put(pattern, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getDateFormat() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(DATE);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(DATE);
            sdfMap.put(DATE, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getDateTimeFormat() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(DATE_TIME);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(DATE_TIME);
            sdfMap.put(DATE_TIME, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getDateTimeWithSecondFormat() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(DATE_TIME_WITH_SECOND);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(DATE_TIME_WITH_SECOND);
            sdfMap.put(DATE_TIME_WITH_SECOND, simpleDateFormat);
        }
        return simpleDateFormat;
    }
}
