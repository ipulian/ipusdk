package com.ipusoft.context.bean;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 4/6/21 6:35 PM
 * desc   : 系统通话记录
 */

@Entity(tableName = "sys_recording")
public class SysRecording implements Serializable {
    private static final long serialVersionUID = 200791783395541596L;
    @ColumnInfo(name = "id")
    private long id;
    //录音文件的绝对路径
    @ColumnInfo(name = "absolute_path")
    private String absolutePath;
    //录音文件名
    @ColumnInfo(name = "file_name")
    private String fileName;
    //录音时长
    @ColumnInfo(name = "duration")
    private int duration;
    //录音的最后修改时间
    @ColumnInfo(name = "file_generate_time")
    private long fileGenerateTime;
    //文件大小
    @ColumnInfo(name = "file_size")
    private long fileSize;
    //联系人号码
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;
    //联系人名字
    @ColumnInfo(name = "phone_name")
    private String phoneName;
    //电话开始时间
    @PrimaryKey()
    @ColumnInfo(name = "call_time")
    private long callTime;
    //记录上传状态0等待上传1正在上传2上传完成3上传失败
    @ColumnInfo(name = "upload_status")
    private int uploadStatus;
    //重试次数
    @ColumnInfo(name = "retry_count")
    private int retryCount;
    //上次重试时间
    @ColumnInfo(name = "last_retry_time")
    private long lastRetryTime;
    //通话结果
    @ColumnInfo(name = "call_result")
    private int callResult;
    //通话类型 1呼入2呼出3未接
    @ColumnInfo(name = "call_type")
    private int callType;
    //录音文件的MD5
    @ColumnInfo(name = "file_md5")
    private String fileMD5;
    //callID
    @ColumnInfo(name = "call_id")
    //@Ignore
    private long callId;

    /**
     * 上传进度
     */
    @Ignore
    private int progress;
    @Ignore
    private boolean isChecked;
    @Ignore
    private String callTimeServer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getFileGenerateTime() {
        return fileGenerateTime;
    }

    public void setFileGenerateTime(long fileGenerateTime) {
        this.fileGenerateTime = fileGenerateTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getLastRetryTime() {
        return lastRetryTime;
    }

    public void setLastRetryTime(long lastRetryTime) {
        this.lastRetryTime = lastRetryTime;
    }

    public int getCallResult() {
        return callResult;
    }

    public void setCallResult(int callResult) {
        this.callResult = callResult;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public long getCallId() {
        return callId;
    }

    public void setCallId(long callId) {
        this.callId = callId;
    }

    public String getCallTimeServer() {
        return callTimeServer;
    }

    public void setCallTimeServer(String callTimeServer) {
        this.callTimeServer = callTimeServer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + callTime == 0 ? 0 : String.valueOf(callTime).hashCode();
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SysRecording other = (SysRecording) obj;
        if (callTime == 0) {
            return other.callTime == 0;
        } else return callTime == other.callTime;
    }
}
