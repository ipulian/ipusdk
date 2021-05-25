package com.ipusoft.context.utils;

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
        if (s != null && s.length != 0) {
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
}
