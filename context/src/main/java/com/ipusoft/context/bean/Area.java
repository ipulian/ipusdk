package com.ipusoft.context.bean;

import com.google.gson.annotations.SerializedName;
import com.ipusoft.context.bean.base.HttpResponse;

import java.util.List;
import java.util.Map;

/**
 * author : GWFan
 * time   : 1/9/21 5:59 PM
 * desc   : 省份-城市
 */

public class Area extends HttpResponse {

    private static final long serialVersionUID = 7673020358510790460L;

    @SerializedName("provinceCity")
    private Map<String, List<String>> areaMap;

    public Map<String, List<String>> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(Map<String, List<String>> areaMap) {
        this.areaMap = areaMap;
    }
}
