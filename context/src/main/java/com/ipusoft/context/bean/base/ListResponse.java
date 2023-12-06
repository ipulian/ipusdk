package com.ipusoft.context.bean.base;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.ipusoft.context.bean.adapter.String2IntegerAdapter;

import java.util.List;

/**
 * author : GWFan
 * time   : 1/23/21 10:44 AM
 * desc   :
 */

public class ListResponse<T> extends HttpResponse {
    private static final long serialVersionUID = -3081347999549719830L;

    /**
     * 列表总数
     */
    @JsonAdapter(String2IntegerAdapter.class)
    private int total;

    /**
     * 列表数据List
     */
    @SerializedName(value = "rows", alternate = {
            "list",
            "smsTpls",
            "rowDatas",
            "data",
            "date",
            "datas",
            "tops",
            "result",
            "storeList",//门店列表
            "asrResults"
    })
    private List<T> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
