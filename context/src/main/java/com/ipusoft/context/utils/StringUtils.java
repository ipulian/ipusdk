package com.ipusoft.context.utils;

import android.content.res.Resources;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.ipusoft.context.IpuSoftSDK;

import java.util.IllegalFormatException;
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
        return StringUtils.isEmpty(str) ? "" : str;
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
        return stringBuilder.toString();
    }

    /**
     * 获取String字符串资源
     *
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        return getString(id, (Object[]) null);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        try {
            return format(IpuSoftSDK.getAppContext().getString(id), formatArgs);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return String.valueOf(id);
        }
    }

    /**
     * Format the string.
     *
     * @param str  The string.
     * @param args The args.
     * @return a formatted string.
     */
    public static String format(@Nullable String str, Object... args) {
        String text = str;
        if (text != null) {
            if (args != null && args.length > 0) {
                try {
                    text = String.format(str, args);
                } catch (IllegalFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
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
}

