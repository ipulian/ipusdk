package com.ipusoft.localcall.cache;


import com.ipusoft.context.bean.SysRecording;

import java.util.ArrayList;
import java.util.List;

/**
 * author : GWFan
 * time   : 4/25/21 5:24 PM
 * desc   :
 */

public class SimAppCache {
    private static final String TAG = "AppCache";

    /**
     * 正在上传和等待上传的任务队列(通话的时间戳作为唯一性标识),防止重复上传
     */
    private static final List<Long> uploadQueue = new ArrayList<>();

    /**
     * 将记录加入去重集合
     *
     * @param sysRecording
     * @return
     */
    public static boolean addFile2UploadTask(SysRecording sysRecording) {
        if (sysRecording == null) {
            return false;
        }
        long callTime = sysRecording.getCallTime();
        if (uploadQueue.contains(callTime)) {
            return false;
        }
        return uploadQueue.add(callTime);
    }

    /**
     * 从队列中移除任务
     *
     * @param sysRecording
     * @return
     */
    public static void removeTaskFromQueue(SysRecording sysRecording) {
        uploadQueue.remove(sysRecording.getCallTime());
    }
}
