package com.ipusoft.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : GWFan
 * time   : 1/9/21 2:07 PM
 * desc   :
 */

public class ArrayUtils {

    /**
     * 创建包含默认元素值的List
     *
     * @param s
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> List<E> createList(E... s) {
        List<E> list = new ArrayList<>();
        if (isNotEmpty(s)) {
            list.addAll(Arrays.asList(s));
        }
        return list;
    }

    /**
     * 判断List是否为空
     *
     * @param list
     * @param <E>
     * @return
     */
    public static <E> boolean isEmpty(List<E> list) {
        return list == null || list.size() == 0;
    }

    public static <E> boolean isEmpty(E[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断List是否不为空
     *
     * @param list
     * @param <E>
     * @return
     */
    public static <E> boolean isNotEmpty(List<E> list) {
        return list != null && list.size() != 0;
    }

    public static <E> boolean isNotEmpty(E[] array) {
        return array != null && array.length != 0;
    }

    /**
     * 如果为null,返回空集合
     *
     * @param list
     * @param <E>
     * @return
     */
    public static <E> List<E> null2Empty(List<E> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static <E> int getListSize(List<E> list) {
        if (isEmpty(list)) {
            return 0;
        }
        return list.size();
    }
}
