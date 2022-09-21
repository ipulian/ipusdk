package com.ipusoft.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * author : GWFan
 * time   : 8/27/21 9:51 AM
 * desc   :
 */

public class ObjectUtils {
    private static final String TAG = "ObjectUtils";

    /**
     * 判断对象是否包含值为null的属性
     *
     * @param obj
     * @return
     */
    public static boolean checkObjFieldIsEmpty(Object obj) {
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object o = f.get(obj);
                if (o instanceof String) {
                    String o1 = (String) o;
                    if (StringUtils.isEmpty(o1)) {
                        return true;
                    }
                } else if (o instanceof List) {
                    List o1 = (List) o;
                    if (ArrayUtils.isEmpty(o1)) {
                        return true;
                    }
                } else {
                    if (f.get(obj) == null) {
                        return true;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkObjFieldIsNotEmpty(Object obj) {
        Class<?> clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (clazz != null && ArrayUtils.isNotEmpty(clazz.getDeclaredFields())) {
                for (Field f : clazz.getDeclaredFields()) {
                    f.setAccessible(true);
                    Object o = null;
                    try {
                        o = f.get(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (o instanceof String) {
                        String o1 = (String) o;
                        if (StringUtils.isNotEmpty(o1)) {
                            return true;
                        }
                    } else if (o instanceof List) {
                        List o1 = (List) o;
                        if (ArrayUtils.isNotEmpty(o1)) {
                            return true;
                        }
                    } else {
                        try {
                            if (f.get(obj) != null) {
                                return true;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Map<String, Object> getObjValue(Object object) {
        String dataStr = new Gson().toJson(object);
        JSONObject json = null;
        Map<String, Object> map = new HashMap<>();
        try {
            json = new JSONObject(dataStr);
            Iterator it = json.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = json.get(key);
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}