package com.ipusoft.utils;

/**
 * author : GWFan
 * time   : 7/27/21 5:33 PM
 * desc   :
 */

public class Utils {

    public static int parseInteger(Integer value) {
        int result = 0;
        if (value != null) {
            result = value;
        }
        return result;
    }

    public static long parseLong(Long value) {
        long result = 0;
        if (value != null) {
            result = value;
        }
        return result;
    }

    /**
     * 两数相除取百分数 66.7%
     *
     * @param a
     * @param b
     * @return
     */
    public static String toPercent(int a, int b) {
        if (a % b == 0) {
            return a / b * 100 + "%";
        } else {
            return (double) Math.round(a / (double) b * 1000) / 10 + "%";
        }
    }
}
