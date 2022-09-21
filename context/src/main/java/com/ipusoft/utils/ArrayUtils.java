package com.ipusoft.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @SafeVarargs
    public static <E> List<E> createLinkedList(E... s) {
        List<E> list = new LinkedList<>();
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
     * @param list 源集合
     * @param <E>  元素类型
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

    /**
     * 数组转集合
     *
     * @param array 原数组
     * @param <E>   元素类型
     * @return 集合
     */
    public static <E> List<E> array2List(E[] array) {
        List<E> list = new ArrayList<>();
        if (isNotEmpty(array)) {
            Collections.addAll(list, array);
        }
        return list;
    }

    /**
     * 集合转数组
     *
     * @param list 源集合
     * @return 数组
     */
    public static String[] stringList2Array(List<String> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.toArray(new String[0]);
    }

    /**
     * 集合转数组
     *
     * @param list 源集合
     * @return 数组
     */
    public static Integer[] integerList2Array(List<Integer> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.toArray(new Integer[0]);
    }

    /**
     * 反转List
     *
     * @param list 源集合
     * @param <E>  元素类型
     * @return 反转后的集合
     */
    public static <E> List<E> reverse(List<E> list) {
        if (list == null) {
            return null;
        }
        int size = list.size();
        int last = size - 1;
        List<E> result = new ArrayList<>(size);
        for (int i = last; i >= 0; --i) {
            final E element = list.get(i);
            result.add(element);
        }
        return result;
    }

    /**
     * 去重
     *
     * @param list
     * @return
     */
    public static <E> List<E> removeDuplicate(List<E> list) {
        if (ArrayUtils.isEmpty(list)) {
            return list;
        }
        Set<E> h = new HashSet<>(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 添加非空元素
     *
     * @param list
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> addNonNull(List<T> list, T... ts) {
        if (isNotEmpty(ts)) {
            for (T t : ts) {
                if (t != null) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * 对集合进行深拷贝
     * 注意需要岁泛型类进行序列化（实现serializable）
     *
     * @param src
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteOut);
        ) {
            outputStream.writeObject(src);
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
                 ObjectInputStream inputStream = new ObjectInputStream(byteIn);
            ) {
                return (List<T>) inputStream.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
