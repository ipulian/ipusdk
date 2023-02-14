package com.ipusoft.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class NetWorkListenerUtils {
    private Context context;
    private Handler mHandler;

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private Timer timer;

    public NetWorkListenerUtils(Context context, Handler mHandler) {
        this.context = context;
        this.mHandler = mHandler;
    }

    public void startShowNetSpeed() {
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
        // 1s后启动任务，每2s执行一次、
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(task, 1000, 1000);

    }

    public void unbindShowNetSpeed() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }

    private void showNetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage();
            msg.what = 100;
            Log.d("TAG", "showNetSpeed: ------>" + speed + "." + speed2);
            msg.obj = changeFlowFormat(speed + "." + String.valueOf(speed2).charAt(0));
            // msg.obj = String.valueOf(speed) + "." + String.valueOf(speed2);

            //  Log.d("TAG", "showNetSpeed: ---->" + msg.obj + "---->" + String.valueOf(speed) + "." + String.valueOf(speed2) + "----->" + new DecimalFormat("#.0").format(speed2));

            mHandler.sendMessage(msg);//更新界面
        }
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            showNetSpeed();
        }
    };

    public String changeFlowFormat(String flow) {
        Double flows = Double.valueOf(flow);
        if (flows > 0 && flows < 1024) {//小于1M
            return flows + "KB";
        } else if (flows >= 1024 && flows < 1048576) {//大于1M小于1G
            int changeM = (int) Math.floor(flows / 1024);//整M数
            int surplusM = (int) Math.floor(flows % 1024);//除M后的余数
            String result = changeM + "MB";
            Log.d("12123", "changeFlowFormat: -------》" + flow + "-------->" + result);
            return result;
        } else if (flows >= 1048576) {//大于1G
            int changeG = (int) Math.floor(flows / 1048576);//整G数
            int surplusG = (int) Math.floor(flows % 1048576);//除G后的余数
            if (surplusG >= 1024) {//余数大于大于1M
                int changeM = (int) Math.floor(surplusG / 1024);
                int surplusM = (int) Math.floor(surplusG % 1024);
                if (surplusM > 0) {//余数大于0KB
                    return changeG + "GB";// + changeM + "MB" + surplusM + "KB";
                } else {//整M，没有余数
                    return changeG + "GB";// + changeM + "MB";
                }
            } else if (surplusG < 1024 && surplusG > 0) {//余数小于1M，大于0K
                int surplusM = (int) Math.floor(surplusG % 1024);
                return changeG + "GB";// + surplusM + "KB";
            } else {
                return changeG + "GB";
            }
        }
        return "0.0KB";
    }
}
