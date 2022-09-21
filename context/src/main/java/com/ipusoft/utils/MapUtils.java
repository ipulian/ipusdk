package com.ipusoft.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * author : GWFan
 * time   : 7/14/21 3:42 PM
 * desc   :
 */

public class MapUtils {

    /**
     * map是否为空
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    /**
     * map是否不为空
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return map != null && map.size() != 0;
    }

    public static <K, V> Map<K, V> createIfNull(Map<K, V> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * map根据value进行排序
     *
     * @param map 源HashMap
     * @return 排序后的HashMap
     */
    public static LinkedHashMap<String, Integer> sortByValue(LinkedHashMap<String, Integer> map) {
        try {
            // HashMap的entry放到List中
            List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
            //  对List按entry的value排序
            Collections.sort(list, (o1, o2) -> {
                if (o1.getValue() < o2.getValue()) {
                    return 1;
                }
                return -1;
            });
            // 将排序后的元素放到LinkedHashMap中
            LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> aa : list) {
                temp.put(aa.getKey(), aa.getValue());
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
