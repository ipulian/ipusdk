package com.ipusoft.utils;

import java.lang.reflect.Field;

/**
 * author : GWFan
 * time   : 10/28/20 4:06 PM
 * desc   :反射工具类
 */

public class ReflectUtils {
    private static final String TAG = "MyReflectUtils";

    /**
     * 获取对象的属性值(不包括继承来的父类属性)
     *
     * @param fieldName 属性名
     * @param obj       对象
     * @param <T>       属性值的类型
     * @return 属性值
     */
    public static <T> T getValueByFieldName(String fieldName, Object obj) {
        Class<?> Clazz = obj.getClass();
        Field field;
        if ((field = getField(Clazz, fieldName)) == null)
            return null;
        field.setAccessible(true);
        Object o = null;
        try {
            o = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (o == null) {
            return null;
        }
        return (T) o;
    }

    /**
     * 获取对象的属性值(包括继承来的父类属性)
     *
     * @param fieldName 属性名
     * @param obj       对象
     * @param <T>       属性值的类型
     * @return 属性值
     */
    public static <T> T getValueBySuperFieldName(String fieldName, Object obj) {
        Class<?> clazz = obj.getClass();
        Object o = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    o = field.get(obj);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (o == null) {
            return null;
        }
        return (T) o;
    }

    /**
     * 获取类的字段
     *
     * @param clazz        类
     * @param propertyName 属性名
     * @return 字段
     */
    public static Field getField(Class<?> clazz, String propertyName) {
        if (clazz == null)
            return null;
        try {
            return clazz.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给对象的属性赋值(不包括继承来的父类属性)
     *
     * @param fieldName 属性名
     * @param value     值
     * @param t         对象
     * @param <T>       对象类型
     * @return 赋值后的对象
     */
    public static <T> T setValueByFieldName(String fieldName, String value, T t) {
        try {
            Field name = t.getClass().getDeclaredField(fieldName);
            name.setAccessible(true);
            name.set(t, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 给对象的属性赋值(包括继承来的父类属性)
     *
     * @param fieldName 属性名
     * @param value     值
     * @param t         对象
     * @param <T>       对象类型
     * @return 赋值后的对象
     */
    public static <T> T setValueBySuperFieldName(String fieldName, String value, T t) {
        Class<?> clazz = t.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (type == Integer.class) {
                        if (StringUtils.isNotEmpty(value)) {
                            field.set(t, Integer.parseInt(value));
                        }
                    } else {
                        field.set(t, value);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}
