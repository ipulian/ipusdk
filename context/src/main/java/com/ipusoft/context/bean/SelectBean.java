package com.ipusoft.context.bean;

import com.ipusoft.utils.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * author : GWFan
 * time   : 2021/11/10 09:09
 * desc   : key-value
 */

public class SelectBean implements Serializable {
    private String key;
    private String value;
    private int icon;

    public SelectBean() {
    }

    public SelectBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * 用户唯一性判定 key的值是否相同
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        SelectBean other = (SelectBean) obj;
        if (StringUtils.isEmpty(key)) {
            return StringUtils.isEmpty(other.key);
        } else return key.equals(other.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}
