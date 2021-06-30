package com.ipusoft.mmkv;

import android.os.Parcelable;

import com.ipusoft.mmkv.config.DSConfig;
import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Set;

/**
 * author : GWFan
 * time   : 5/12/21 9:21 AM
 * desc   :
 */

public class AccountMMKV {

    private static final MMKV mv = MMKV.mmkvWithID(DSConfig.ACCOUNT_DS);

    private AccountMMKV() {
    }

    public static void set(String key, Object object) {
        if (object instanceof String) {
            mv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mv.encode(key, (Long) object);
        } else if (object instanceof Double) {
            mv.encode(key, (Double) object);
        } else if (object instanceof byte[]) {
            mv.encode(key, (byte[]) object);
        } else {
            mv.encode(key, object.toString());
        }
    }

    public static void setSet(String key, Set<String> sets) {
        mv.encode(key, sets);
    }

    public static void setParcelable(String key, Parcelable obj) {
        mv.encode(key, obj);
    }

    public static Integer getInt(String key) {
        return mv.decodeInt(key, 0);
    }

    public static Double getDouble(String key) {
        return mv.decodeDouble(key, 0.00);
    }

    public static Long getLong(String key) {
        return mv.decodeLong(key, 0L);
    }

    public static Long getLong(String key, long defVal) {
        return mv.decodeLong(key, defVal);
    }

    public static Boolean getBoolean(String key) {
        return mv.decodeBool(key, false);
    }

    public static Boolean getBoolean(String key, boolean deft) {
        return mv.decodeBool(key, deft);
    }

    public static Float getFloat(String key) {
        return mv.decodeFloat(key, 0F);
    }

    public static byte[] getBytes(String key) {
        return mv.decodeBytes(key);
    }

    public static String getString(String key) {
        return mv.decodeString(key, "");
    }

    public static Set<String> getStringSet(String key) {
        return mv.decodeStringSet(key, Collections.<String>emptySet());
    }

    public static Parcelable getParcelable(String key) {
        return mv.decodeParcelable(key, null);
    }

    /**
     * 移除某个key对
     */
    public static void removeKey(String key) {
        mv.removeValueForKey(key);
    }

    /**
     * 移除部分key
     */
    public static void removeSomeKey(String[] keyArray) {
        mv.removeValuesForKeys(keyArray);
    }

    /**
     * 清除所有key
     */
    public static void clearAll() {
        mv.clearAll();
    }

    /**
     * 判断是否含有某个key
     */
    public static boolean hasKey(String key) {
        return mv.containsKey(key);
    }
}
