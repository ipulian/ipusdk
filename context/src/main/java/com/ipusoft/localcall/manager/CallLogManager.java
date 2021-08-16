package com.ipusoft.localcall.manager;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.localcall.bean.SysCallLog;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.component.HowToOpenRecordingDialog;
import com.ipusoft.localcall.constant.CallLogCallsType;
import com.ipusoft.localcall.constant.UploadStatus;
import com.ipusoft.localcall.datastore.SimDataRepo;
import com.ipusoft.localcall.repository.CallLogRepo;
import com.ipusoft.localcall.repository.FileRepository;
import com.ipusoft.localcall.repository.RecordingFileRepo;
import com.ipusoft.localcall.repository.SysRecordingRepo;
import com.ipusoft.logger.XLogger;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.PhoneNumberUtils;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.utils.ThreadUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author : GWFan
 * time   : 4/30/21 10:20 AM
 * desc   :
 */

public class CallLogManager {
    private static final String TAG = "CallLogManager";

    private CallLogManager() {

    }

    private static class CallLogManagerHolder {
        private static final CallLogManager INSTANCE = new CallLogManager();
    }

    public static CallLogManager getInstance() {
        return CallLogManagerHolder.INSTANCE;
    }

    /**
     * 查询通话记录合并录音
     */
    public void queryCallLogAndRecording(IObserver<Boolean> observe) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            List<SysRecording> list = new ArrayList<>();
            UploadSysRecordingBean uploadSysCallLog = SimDataRepo.getUploadSysCallLog();
            if (CommonDataRepo.getUploadLocalRecord() && uploadSysCallLog.isFlag()) {
                XLogger.d("5s后系统数据库中查数据");
                CallLogRepo.getInstance().querySysCallLog(new IObserver<List<SysCallLog>>() {
                    @Override
                    public void onNext(@NotNull @NonNull List<SysCallLog> sysCallLogs) {
                        boolean flag = false;
                        List<String> phoneList = new ArrayList<>();
                        long maxTimestamp = uploadSysCallLog.getTimestamp();
                        XLogger.d("maxTimestamp1：" + maxTimestamp);
                        if (ArrayUtils.isNotEmpty(sysCallLogs)) {
                            for (SysCallLog callLog : sysCallLogs) {
                                if (callLog.getBeginTime() > maxTimestamp) {
                                    maxTimestamp = callLog.getBeginTime();
                                }
                                if (callLog.getDuration() != 0 ||
                                        callLog.getCallType() == CallLogCallsType.INCOMING_TYPE.getType()
                                        || callLog.getCallType() == CallLogCallsType.OUTGOING_TYPE.getType()) {
                                    flag = true;
                                }
                                phoneList.add(callLog.getPhoneNumber());
                            }
                        }
                        if (flag && ArrayUtils.isNotEmpty(phoneList)) {
                            XLogger.d("phoneList：" + GsonUtils.toJson(phoneList));
                            XLogger.d("maxTimestamp2：" + maxTimestamp);
                            long finalMaxTimestamp = maxTimestamp;
                            RecordingFileRepo.getInstance().queryRecordingFile(new IObserver<List<File>>() {
                                @Override
                                public void onNext(@NotNull @NonNull List<File> files) {
                                    Map<String, File> fileMap = new HashMap<>();// 联系人号码+时间和录音文件的map
                                    XLogger.i("录音文件->files：" + GsonUtils.toJson(files));
                                    for (File file : files) {
                                        if (file != null) {
                                            CommonDataRepo.setLocalRecordPath(file.getParent());
                                            String fileName = StringUtils.trim(file.getName());
                                            String[] phoneFormString = PhoneNumberUtils.getPhoneFormString(fileName);
                                            if (phoneFormString != null && phoneFormString.length != 0) {
                                                for (String str : phoneFormString) {
                                                    if (phoneList.contains(str)) {
                                                        fileMap.put(phoneFormString[0] + "_" + file.lastModified(), file);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    XLogger.i("联系人号码+时间和录音文件的map：" + GsonUtils.toJson(fileMap));
                                    SysRecording recording;
                                    String phoneNumber;
                                    long beginTime;
                                    int duration, callResult;
                                    Map<File, File> fileWaitToCopy = new HashMap<>();
                                    boolean isShow = false;
                                    for (SysCallLog callLog : sysCallLogs) {
                                        File file = null;
                                        phoneNumber = callLog.getPhoneNumber();
                                        beginTime = callLog.getBeginTime();//通话开始时间
                                        duration = callLog.getDuration();//通话时长
                                        callResult = callLog.getCallResult();
                                        long minDiff = Long.MAX_VALUE;
                                        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                                            String phoneTime = entry.getKey();
                                            String[] s = phoneTime.split("_");
                                            /*
                                             * 号码匹配，时间也对应
                                             */
                                            if (StringUtils.equals(s[0], phoneNumber)) {
                                                long timeDiff = entry.getValue().lastModified() - (beginTime + duration);
                                                if (timeDiff >= 0 && timeDiff <= minDiff) {
                                                    minDiff = timeDiff;
                                                    file = entry.getValue();
                                                }
                                            }
                                        }
                                        recording = new SysRecording();
                                        recording.setDuration(callLog.getDuration());
                                        recording.setCallTime(callLog.getBeginTime());
                                        recording.setPhoneName(callLog.getName());
                                        recording.setPhoneNumber(phoneNumber);
                                        recording.setCallType(callLog.getCallType());
                                        recording.setCallResult(callResult);
                                        recording.setCallTimeServer(callLog.getCallTime());
                                        if (callLog.getCallId() != 0) {
                                            recording.setCallId(callLog.getCallId());
                                        } else {
                                            recording.setCallId(System.currentTimeMillis());
                                        }
                                        recording.setUploadStatus(UploadStatus.WAIT_UPLOAD.getStatus());
                                        if (file != null) {
                                            File nFile = new File(FileUtilsKt.getAudioPath(AppContext.getAppContext()) + "/" + file.getName());
                                            recording.setAbsolutePath(nFile.getAbsolutePath());
                                            recording.setFileName(file.getName());
                                            recording.setFileGenerateTime(file.lastModified());
                                            recording.setFileSize(file.length());
                                            recording.setFileMD5(FileUtilsKt.getFileMD5ToString(file));
                                            fileWaitToCopy.put(file, nFile);
                                        } else {
                                            XLogger.d("duration：" + duration + "\tcallResult：" + callResult + "\n");
                                            //已接通，但是未找到录音文件，或者用户没有打开录音功能
                                            if (!isShow && (duration != 0 || callResult == 0)) {
                                                isShow = true;
                                                ThreadUtils.runOnUiThread(CallLogManager.this::showTipDialog);
                                            }
                                        }
                                        list.add(recording);
                                    }

                                    SimDataRepo.setUploadSysCallLog(true, finalMaxTimestamp);
//
                                    FileRepository.copyFileAsync(fileWaitToCopy);
                                    /*
                                     * 入库
                                     */
                                    SysRecordingRepo.insert(list);
                                    /*
                                     * 加入任务队列
                                     */
                                    XLogger.d("加入上传队列1：" + GsonUtils.toJson(list));
                                    UploadManager.getInstance().addRecordingList2Task(list);

                                    emitter.onNext(true);
                                    emitter.onComplete();

                                }
                            });
                        } else {
                            XLogger.d("phoneList is empty ? " + ArrayUtils.isEmpty(phoneList));
                            if (ArrayUtils.isEmpty(sysCallLogs)) {
                                XLogger.d("没有通话记录");
                            }
                            if (flag) {
                                XLogger.d("录音时长都为0");
                            }

                            SysRecording recording;
                            String phoneNumber;
                            for (SysCallLog callLog : sysCallLogs) {
                                phoneNumber = callLog.getPhoneNumber();
                                recording = new SysRecording();
                                recording.setDuration(callLog.getDuration());
                                recording.setCallTime(callLog.getBeginTime());
                                recording.setPhoneName(callLog.getName());
                                recording.setPhoneNumber(phoneNumber);
                                recording.setUploadStatus(UploadStatus.WAIT_UPLOAD.getStatus());
                                recording.setCallResult(callLog.getCallResult());
                                recording.setCallType(callLog.getCallType());

                                if (callLog.getCallId() != 0) {
                                    recording.setCallId(callLog.getCallId());
                                } else {
                                    recording.setCallId(System.currentTimeMillis());
                                }
                                recording.setCallTimeServer(callLog.getCallTime());

                                list.add(recording);
                            }

                            SimDataRepo.setUploadSysCallLog(true, maxTimestamp);

                            /**
                             * 入库
                             */
                            SysRecordingRepo.insert(list);
                            /**
                             * 加入任务队列
                             */
                            XLogger.d("加入上传队列2：" + GsonUtils.toJson(list));
                            UploadManager.getInstance().addRecordingList2Task(list);

                            emitter.onNext(true);
                            emitter.onComplete();

                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observe);
    }

    private void showTipDialog() {
        HowToOpenRecordingDialog.getInstance().show();
    }
}
