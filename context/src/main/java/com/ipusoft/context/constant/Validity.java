package com.ipusoft.context.constant;


import java.util.ArrayList;
import java.util.List;

/**
 * author : GWFan
 * time   : 1/10/21 3:28 PM
 * desc   : 线索 有效性
 */

public enum Validity {

    VALIDITY_ALL("全部", -1),
    VALIDITY_("有效", 1),
    UN_VALIDITY("无效", 2),
    VALIDITY_NULL("无", 0);

    private final String key;
    private final int value;

    Validity(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    /**
     * 根据key 返回value
     *
     * @param key
     * @return
     */
    public static int getValueByKey(String key) {
        for (Validity validity : Validity.values()) {
            if (validity.getKey().equals(key)) {
                return validity.getValue();
            }
        }
        return VALIDITY_ALL.getValue();
    }

    public static String getKeyByValue(int value) {
        for (Validity validity : Validity.values()) {
            if (validity.getValue() == value) {
                return validity.getKey();
            }
        }
        return "";
    }

    /**
     * 返回所有的key
     *
     * @return
     */
    public static List<String> getAllKeys() {
        List<String> list = new ArrayList<>();
        for (Validity validity : Validity.values()) {
            list.add(validity.getKey());
        }
        return list;
    }

    /**
     * 编辑线索的Keys
     *
     * @return
     */
    public static List<String> getEditKeys() {
        List<String> list = new ArrayList<>();
        for (Validity validity : Validity.values()) {
            if (!validity.getKey().equals(VALIDITY_ALL.getKey())) {
                list.add(validity.getKey());
            }
        }
        return list;
    }
}
