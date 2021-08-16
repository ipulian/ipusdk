package com.ipusoft.localcall.constant;

/**
 * author : GWFan
 * time   : 4/15/21 9:34 AM
 * desc   : 文件上传状态
 */

public enum UploadStatus {

    NONE_STATUS("", -1),
    WAIT_UPLOAD("等待上传", 0),
    UPLOADING("正在上传", 1),
    UPLOAD_SUCCEED("上传完成", 2),
    UPLOAD_FAILED("上传失败", 3),
    UPLOAD_IGNORE("忽略", 4);

    private final String key;
    private final int status;

    UploadStatus(String key, int status) {
        this.key = key;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public int getStatus() {
        return status;
    }
}
