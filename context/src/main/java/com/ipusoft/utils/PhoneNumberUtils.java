package com.ipusoft.utils;


import java.util.ArrayList;
import java.util.List;
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
    public static List<String> getPhoneFormString(String num) {
        if (StringUtils.isEmpty(num)) {
            return null;
        }
        Pattern pattern1 = Pattern.compile("(1[3-9])\\d{9}");
        Pattern pattern2 = Pattern.compile("(0[1][0])\\d{7}");
        Pattern pattern3 = Pattern.compile("(0[1][0]-)\\d{7}");
        Pattern pattern4 = Pattern.compile("(0[1][0])\\d{8}");
        Pattern pattern5 = Pattern.compile("(0[1][0]-)\\d{8}");
        Pattern pattern6 = Pattern.compile("(0[2][012345789])\\d{7}");
        Pattern pattern7 = Pattern.compile("(0[2][012345789]-)\\d{7}");
        Pattern pattern8 = Pattern.compile("(0[2][012345789])\\d{8}");
        Pattern pattern9 = Pattern.compile("(0[2][012345789]-)\\d{8}");
        Pattern pattern10 = Pattern.compile("(0[3456789][0-9][0-9])\\d{7}");
        Pattern pattern11 = Pattern.compile("(0[3456789][0-9][0-9]-)\\d{7}");
        Pattern pattern12 = Pattern.compile("(0[3456789][0-9][0-9])\\d{8}");
        Pattern pattern13 = Pattern.compile("(0[3456789][0-9][0-9]-)\\d{8}");

        List<Pattern> patterns = ArrayUtils.createList(pattern1, pattern2, pattern3, pattern4, pattern5,
                pattern6, pattern7, pattern8, pattern9, pattern10, pattern11, pattern12, pattern13);

        List<String> list = new ArrayList<>();

        for (Pattern p : patterns) {
            Matcher matcher = p.matcher(num);
            while (matcher.find()) {
                String phone = matcher.group();
                if (!list.contains(phone)) {
                    list.add(phone);
                }
            }
        }
        return list;
    }


}
