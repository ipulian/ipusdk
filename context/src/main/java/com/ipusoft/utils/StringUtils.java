package com.ipusoft.utils;

import android.util.Base64;

import java.util.List;

/**
 * author : GWFan
 * time   : 5/24/21 4:49 PM
 * desc   :
 */

public class StringUtils {

    private static final char[] HEX_DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final char[] HEX_DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    //数字
    public static final String REG_NUMBER = ".*\\d+.*";
    //小写字母
    public static final String REG_UPPERCASE = ".*[A-Z]+.*";
    //大写字母
    public static final String REG_LOWERCASE = ".*[a-z]+.*";
    //特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
    public static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    /**
     * null 转成 ""
     *
     * @param str
     * @return
     */
    public static String null2Empty(String str) {
        return isEmpty(str) ? "" : str;
    }

    /**
     * trim掉字符串中的所有空格
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return null2Empty(str).replaceAll("\\s*", "");
    }

    /**
     * trim掉字符串中的首尾空格
     *
     * @param str
     * @return
     */
    public static String trim2(String str) {
        return null2Empty(str).trim();
    }

    public static String trim(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return "";
        }
        return null2Empty(stringBuffer.toString()).replaceAll("\\s*", "");
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * List转字符串
     *
     * @param elements
     * @param <T>
     * @return
     */
    public static <T> String join(final List<T> elements) {
        return join(elements, ",");
    }

    public static <T> String join(final List<T> array, final String delimiter) {
        if (array == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : array) {
            stringBuilder.append(obj);
            stringBuilder.append(delimiter);
        }
        String result = stringBuilder.toString();
        if (result.endsWith(delimiter)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 字符串中是否包含数字
     *
     * @param str
     * @return
     */
    public static boolean hasNumber(String str) {
        return str.matches(REG_NUMBER);
    }

    /**
     * 字符串中是否包含大写字母
     *
     * @param str
     * @return
     */
    public static boolean hasUppercase(String str) {
        return str.matches(REG_UPPERCASE);
    }

    /**
     * 字符串是否包含小写字母
     *
     * @param str
     * @return
     */
    public static boolean hasLowercase(String str) {
        return str.matches(REG_LOWERCASE);
    }

    /**
     * 字符串是否包含特殊字符
     *
     * @param str
     * @return
     */
    public static boolean hasSymbol(String str) {
        return str.matches(REG_SYMBOL);
    }

    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) return "";
        char[] hexDigits = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    public static String base64Encode2String(final byte[] input) {
        if (input == null || input.length == 0) return "";
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * 字符串截取
     *
     * @param str   源字符串
     * @param start 开始位置
     * @param end   结束位置
     * @return 新字符串
     */
    public static String subString(String str, int start, int end) {
        str = null2Empty(str);
        if (str.length() >= end) {
            str = str.substring(start, end);
        }
        return str;
    }

    /**
     * 字符串中字符替换
     *
     * @param str         源字符串
     * @param regex       被替换的子串
     * @param replacement 替换成子串
     * @return 新字符串
     */
    public static String replaceAll(String str, String regex, String replacement) {
        str = null2Empty(str);
        if (isNotEmpty(str)) {
            str = str.replaceAll(regex, replacement);
        }
        return str;
    }

    /**
     * @param str        源字符串
     * @param replaceStr 替换成字符串
     * @return 若源字符串为空，将返回指定字符串
     */
    public static String replaceEmpty(String str, String replaceStr) {
        if (isEmpty(str)) {
            str = replaceStr;
        }
        return str;
    }
}

