package com.ipusoft.context.bean;

import com.ipusoft.utils.StringUtils;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 7/14/21 3:14 PM
 * desc   : 本地录音文件路径
 */

public class LocalRecordPath implements Serializable {
    //权重
    private int weight;
    //路径
    private String path;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        LocalRecordPath other = (LocalRecordPath) obj;
        if (StringUtils.isEmpty(path)) {
            return StringUtils.isEmpty(other.path);
        } else return path.equals(other.path);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((StringUtils.isEmpty(path)) ? 0 : path.hashCode());
        return result;
    }
}
