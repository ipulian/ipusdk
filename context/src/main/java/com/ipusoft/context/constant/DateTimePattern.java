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
    public static final String DATE2 = "yyyy.MM.dd";
    public static final String DATE3 = "yyyy/MM/dd";

    public static final String YEAR_MONTH = "yyyy-MM";
    public static final String MONTH_DAY = "MM-dd";
    public static final String MONTH_DAY2 = "MM/dd";
    public static final String DAY = "dd";
    public static final String DAY2 = "d";
    public static final String MONTH = "MM";

    public static final String TIME = "HH:mm";

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME2 = "MM-dd HH:mm";

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
    public static SimpleDateFormat getDateFormat3() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(DATE3);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(DATE3);
            sdfMap.put(DATE3, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getTimeFormat() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(TIME);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(TIME);
            sdfMap.put(TIME, simpleDateFormat);
        }
        return simpleDateFormat;
    }


    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getMonthDayFormat() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(MONTH_DAY);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(MONTH_DAY);
            sdfMap.put(MONTH_DAY, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getMonthDayFormat2() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(MONTH_DAY2);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(MONTH_DAY2);
            sdfMap.put(MONTH_DAY2, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getYearMonthFormat() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(YEAR_MONTH);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(YEAR_MONTH);
            sdfMap.put(YEAR_MONTH, simpleDateFormat);
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
    public static SimpleDateFormat getDateTimeFormat2() {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = sdfMap.get(DATE_TIME2);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(DATE_TIME2);
            sdfMap.put(DATE_TIME2, simpleDateFormat);
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
