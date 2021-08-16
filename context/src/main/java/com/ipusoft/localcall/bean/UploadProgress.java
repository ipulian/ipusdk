package com.ipusoft.localcall.bean;


import com.ipusoft.context.bean.SysRecording;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 4/27/21 6:22 PM
 * desc   :
 */

public class UploadProgress implements Serializable {

    private SysRecording recording;
    private int progress;

    public UploadProgress(SysRecording recording, int progress) {
        this.recording = recording;
        this.progress = progress;
    }

    public SysRecording getRecording() {
        return recording;
    }

    public void setRecording(SysRecording recording) {
        this.recording = recording;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
