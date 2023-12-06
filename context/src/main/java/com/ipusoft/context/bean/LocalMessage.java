package com.ipusoft.context.bean;

import java.io.Serializable;

/**
 * @author : GWFan
 * time   : 2023/6/16 17:49
 * desc   :
 */

public class LocalMessage implements Serializable {
    private long id;
    private int type;//1未找到录音，2未设置主叫号码，3质检命中
    private String timestamp;
    private int read = 0;//0未读；1已读

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
