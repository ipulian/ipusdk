package com.ipusoft.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : GWFan
 * time   : 7/1/20 7:13 PM
 * desc   : 电话号码相关工具类
 */

public class PhoneNumberUtils {
    private static final String TAG = "PhoneNumberUtils";

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^[1][2,3,4,5,6,7,8,9][0-9]{9}$");
        return p.matcher(str).matches();
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Matcher m;
        Pattern p1 = Pattern.compile("^[0][0-9]{2,3}[0-9]{5,10}$");  // 验证带区号的
        //Pattern p2 = Pattern.compile("^[1-9][0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
        } else {
            return false;
        }
        return m.matches();
    }

    /**
     * 提取字符串中的电话号码
     *
     * @param num
     * @return
     */
    public static String[] getPhoneFormString(String num) {
        if (StringUtils.isEmpty(num)) {
            return null;
        }
        StringBuilder bf = new StringBuilder();
        Pattern pattern = Pattern.compile("((1[3-9])\\d{9})|((0[1-9])\\d{7,9})|((0[1-9][0-9]-)\\d{7,9})|((0[1-9][0-9][0-9]-)\\d{7,9})");
        Matcher matcher = pattern.matcher(num);
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        String s = bf.toString();
        String[] result = null;
        if (StringUtils.isNotEmpty(s)) {
            result = s.split(",");
        }
        return result;
    }
}
