package com.ipusoft.context.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

/**
 * author : GWFan
 * time   : 4/6/23 6:35 PM
 * desc   : 外呼任务
 */

@Entity(tableName = "call_task")
public class CallTask implements Serializable {
    private static final long serialVersionUID = 200791783395541596L;

    @Ignore
    private String header;

    @Ignore
    private List<CallTaskDetails> detailsList;

    public CallTask() {
    }

    public CallTask(String header) {
        this.header = header;
    }

    @PrimaryKey()
    @ColumnInfo(name = "id")
    private long id;
    //任务名称
    @ColumnInfo(name = "name")
    private String name;
    //总数
    @ColumnInfo(name = "total")
    private int total;
    //已执行
    @ColumnInfo(name = "executed")
    private int executed;
    //状态：1待执行，2执行中，3已完成
    @ColumnInfo(name = "status")
    private int status;

    //备用字段
    @ColumnInfo(name = "extend1")
    private int extend1;

    @ColumnInfo(name = "extend2")
    private int extend2;

    @ColumnInfo(name = "extend3")
    private int extend3;

    @ColumnInfo(name = "extend4")//任务预计什么时候执行
    private String executeTime;

    @ColumnInfo(name = "extend5")
    private String extend5;

    @ColumnInfo(name = "extend6")
    private String extend6;

    //创建时间
    @ColumnInfo(name = "ctime")
    private String ctime;
    //是否删除
    @ColumnInfo(name = "deleted")
    private int deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getExecuted() {
        return executed;
    }

    public void setExecuted(int executed) {
        this.executed = executed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExtend1() {
        return extend1;
    }

    public void setExtend1(int extend1) {
        this.extend1 = extend1;
    }

    public int getExtend2() {
        return extend2;
    }

    public void setExtend2(int extend2) {
        this.extend2 = extend2;
    }

    public int getExtend3() {
        return extend3;
    }

    public void setExtend3(int extend3) {
        this.extend3 = extend3;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5;
    }

    public String getExtend6() {
        return extend6;
    }

    public void setExtend6(String extend6) {
        this.extend6 = extend6;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<CallTaskDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<CallTaskDetails> detailsList) {
        this.detailsList = detailsList;
    }
}
