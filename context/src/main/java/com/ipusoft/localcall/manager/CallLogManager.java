package com.ipusoft.localcall.manager;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.LocalMessage;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.context.iface.OnMyValueClickListener;
import com.ipusoft.context.manager.ThreadPoolManager;
import com.ipusoft.localcall.bean.SysCallLog;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.constant.UploadStatus;
import com.ipusoft.localcall.datastore.SimDataRepo;
import com.ipusoft.localcall.repository.CallLogRepo;
import com.ipusoft.localcall.repository.FileRepository;
import com.ipusoft.localcall.repository.RecordingFileRepo;
import com.ipusoft.localcall.repository.SysRecordingRepo;
import com.ipusoft.localcall.view.dialog.HowToOpenRecordingDialog;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.DateTimeUtils;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.MapUtils;
import com.ipusoft.utils.PhoneNumberUtils;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.utils.ThreadUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
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
    public void queryCallLogAndRecording(OnMyValueClickListener<Boolean> listener) {
        //通讯录中姓名和手机号的map
        readContacts(new IObserver<Map<String, String>>() {
            @Override
            public void onNext(@NonNull Map<String, String> namePhoneMap) {
                Log.d(TAG, "onDisConnectedListener: ---------------------------2");
                if (MapUtils.isNotEmpty(namePhoneMap)) {
                    XLog.d("namePhoneMap-------->" + namePhoneMap.size());
                } else {
                    XLog.d("namePhoneMap-------->0");
                }

                ThreadPoolManager.newInstance().addExecuteTask(() -> {
                    Log.d(TAG, "onDisConnectedListener: ---------------------------3");
//                Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                    List<SysRecording> list = new ArrayList<>();
                    UploadSysRecordingBean uploadSysCallLog = SimDataRepo.getUploadSysCallLog();
                    //上传通话记录和录音
                    if (uploadSysCallLog.isFlag() && CommonDataRepo.getUploadLocalRecord()) {
                        XLog.d(TAG + "->5s后系统数据库中查数据");
                        CallLogRepo.getInstance().querySysCallLog(new IObserver<List<SysCallLog>>() {
                            @Override
                            public void onNext(@NotNull @NonNull List<SysCallLog> sysCallLogs) {
                                Log.d(TAG, "onDisConnectedListener: ---------------------------4");
                                boolean flag = false;
                                List<String> phoneList = new ArrayList<>();
                                long maxTimestamp = uploadSysCallLog.getTimestamp();
                                XLog.d(TAG + "->maxTimestamp1：" + maxTimestamp);
                                if (ArrayUtils.isNotEmpty(sysCallLogs)) {
                                    for (SysCallLog callLog : sysCallLogs) {
                                        if (callLog.getBeginTime() > maxTimestamp) {
                                            maxTimestamp = callLog.getBeginTime();
                                        }
                                        if (callLog.getDuration() != 0) {
                                            if (!flag) {
                                                flag = true;
                                            }
                                        }
                                        phoneList.add(callLog.getPhoneNumber());
                                    }
                                }
                                if (flag && ArrayUtils.isNotEmpty(phoneList)) {
                                    XLog.d(TAG + "->phoneList：" + GsonUtils.toJson(phoneList));
                                    XLog.d(TAG + "->maxTimestamp2：" + maxTimestamp);
                                    long finalMaxTimestamp = maxTimestamp;
                                    RecordingFileRepo.getInstance().queryRecordingFile(namePhoneMap, files -> {
                                        Map<String, File> fileMap = new HashMap<>();// 联系人号码+时间和录音文件的map
                                        Log.d(TAG, "onNext: -----------------1");
                                        XLog.d(TAG + "->录音文件->files：\n");
                                        XLog.json(GsonUtils.toJson(files) + "\n");
                                        for (File file : files) {
                                            if (file != null) {
                                                CommonDataRepo.setLocalRecordPath(file.getParent());
                                                String fileName = StringUtils.trim(file.getName());
                                                String[] phoneFormString = PhoneNumberUtils.getPhoneFormString(fileName);

                                                Log.d(TAG, "onNext: ----------->" + GsonUtils.toJson(phoneFormString));
                                                if (phoneFormString != null && phoneFormString.length != 0) {
                                                    for (String str : phoneFormString) {
                                                        if (phoneList.contains(str)) {
                                                            fileMap.put(str + "_" + file.lastModified(), file);
                                                        }
                                                    }
                                                } else {
                                                    //文件名中没有匹配到手机号,通过通讯录做匹配
                                                    if (namePhoneMap.size() != 0) {//拿到了通讯录数据
                                                        String substring = fileName.substring(0, fileName.lastIndexOf("."));
                                                        for (Map.Entry<String, String> entry : namePhoneMap.entrySet()) {
                                                            if (substring.contains(entry.getKey())) {
                                                                fileMap.put(entry.getValue() + "_" + file.lastModified(), file);
                                                            }
                                                        }
                                                    } else {//没有拿到通讯录数据
                                                        //TODO
                                                    }
                                                }
                                            }
                                        }
                                        Log.d(TAG, "onNext: -----------------2");
                                        //通过手机号和通讯录都额密友匹配到。对于个别机型（比如OnePlus，录音文件名中不包含任何特征信息）
                                        XLog.i(TAG + "->联系人号码+时间和录音文件的map：\n");
                                        XLog.json(GsonUtils.toJson(fileMap) + "\n");
                                        SysRecording recording;
                                        String phoneNumber;
                                        long beginTime, releaseTime;
                                        int duration, callResult;
                                        Map<File, File> fileWaitToCopy = new HashMap<>();
                                        boolean isShow = false;
                                        for (SysCallLog callLog : sysCallLogs) {
                                            File file = null;
                                            phoneNumber = callLog.getPhoneNumber();
                                            beginTime = callLog.getBeginTime();//通话开始时间
                                            releaseTime = callLog.getEndTime();//通话结束时间
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
                                            Log.d(TAG, "onNext: -----------------3");
                                            recording = new SysRecording();
                                            recording.setDuration(callLog.getDuration());
                                            recording.setCallTime(callLog.getBeginTime());
                                            recording.setReleaseTime(callLog.getEndTime());
                                            recording.setPhoneName(callLog.getName());
                                            recording.setPhoneNumber(phoneNumber);
                                            recording.setCaller(callLog.getHostNumber());
                                            recording.setCallType(callLog.getCallType());
                                            recording.setCallResult(callResult);
                                            recording.setCallTimeServer(callLog.getCallTime());
                                            if (callLog.getCallId() != 0) {
                                                recording.setCallId(callLog.getCallId());
                                            } else {
                                                recording.setCallId(System.currentTimeMillis());
                                            }
                                            recording.setUploadStatus(UploadStatus.WAIT_UPLOAD.getStatus());
                                            Log.d(TAG, "onNext: -----------------4" + (file == null));
                                            if (file != null) {
                                                Log.d(TAG, "onNext: -----------------5");
                                                //  file = new File(Environment.getExternalStorageDirectory() + "/sounds/callrecord/131_202.amr");
                                                File nFile = new File(FileUtilsKt.getAudioPath(AppContext.getAppContext()) + "/" + file.getName());
//                                                          Log.d(TAG, "onNext: .de---------->" + file.getAbsolutePath());
//                                                          Log.d(TAG, "onNext: .de---------->" + file.getName());
                                                // nFile = new File("/storage/emulated/0/Quark/Download/138_220.amr");
                                                recording.setAbsolutePath(nFile.getAbsolutePath());
                                                recording.setFileName(file.getName());
                                                recording.setFileGenerateTime(file.lastModified());
                                                recording.setFileSize(file.length());
                                                recording.setFileMD5(FileUtilsKt.getFileMD5ToString(file));
                                                //将系统录音中的录音文件转存到App路径中
                                                fileWaitToCopy.put(file, nFile);
                                            } else {
                                                XLog.d(TAG + "->duration：" + duration + "\tcallResult：" + callResult + "\n");
                                                XLog.d(TAG + "->已接通，但是未找到录音文件，或者用户没有打开录音功能");
                                                //已接通，但是未找到录音文件，或者用户没有打开录音功能
                                                if (!isShow && duration != 0) {
                                                    isShow = true;
                                                    ThreadUtils.runOnUiThread(CallLogManager.this::showTipDialog);

                                                    LocalMessage localMessage = new LocalMessage();
                                                    localMessage.setId(System.currentTimeMillis());
                                                    localMessage.setTimestamp(DateTimeUtils.getCurrentTime(DateTimePattern.getDateTimeWithSecondFormat()));
                                                    localMessage.setType(1);
                                                    CommonDataRepo.addNotificationMessage(localMessage);

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
                                        XLog.d(TAG + "->加入上传队列1：\n");
                                        XLog.json(GsonUtils.toJson(list) + "\n");
                                        UploadManager.getInstance().addRecordingList2Task(list);

//                                                emitter.onNext(true);
//                                                emitter.onComplete();
//
                                        if (listener != null) {
                                            listener.onMyValueCallBack(true);
                                        }

                                    });
                                } else {
                                    XLog.d(TAG + "->phoneList is empty ? " + ArrayUtils.isEmpty(phoneList));
                                    if (ArrayUtils.isEmpty(sysCallLogs)) {
                                        XLog.d("->没有通话记录");
                                    }
                                    if (flag) {
                                        XLog.d(TAG + "->录音时长都为0");
                                    }

                                    SysRecording recording;
                                    String phoneNumber;
                                    for (SysCallLog callLog : sysCallLogs) {
                                        if (!CommonDataRepo.checkUploadRecordIdExist(callLog.getCallId() + "")) {
                                            phoneNumber = callLog.getPhoneNumber();
                                            recording = new SysRecording();
                                            recording.setDuration(callLog.getDuration());
                                            recording.setCallTime(callLog.getBeginTime());
                                            recording.setReleaseTime(callLog.getEndTime());
                                            recording.setPhoneName(callLog.getName());
                                            recording.setPhoneNumber(phoneNumber);
                                            recording.setCaller(callLog.getHostNumber());
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
                                        } else {
                                            Log.d(TAG, "onNext: ----=---------->callId已存在");
                                        }
                                    }

                                    SimDataRepo.setUploadSysCallLog(true, maxTimestamp);

                                    /**
                                     * 入库
                                     */
                                    SysRecordingRepo.insert(list);
                                    /**
                                     * 加入任务队列
                                     */
                                    XLog.d(TAG + "->加入上传队列2：" + GsonUtils.toJson(list));
                                    UploadManager.getInstance().addRecordingList2Task(list);

//                                            emitter.onNext(true);
//                                            emitter.onComplete();
//
                                    if (listener != null) {
                                        listener.onMyValueCallBack(true);
                                    }

                                }
                            }
                        });
                    } else {
                        XLog.d(TAG + "->不需要上传相关记录" + uploadSysCallLog.isFlag() + "----->" + CommonDataRepo.getUploadLocalRecord());
                    }
//                        }).subscribeOn(Schedulers.io())
//                        .observeOn(Schedulers.io())
//                        .subscribe(listener);
                });
            }
        });
    }

    private void showTipDialog() {
        HowToOpenRecordingDialog.getInstance().show();
    }

    private void readContacts(Observer<Map<String, String>> observer) {
        Observable.create((ObservableOnSubscribe<Map<String, String>>) emitter -> {
                    Map<String, String> map = new HashMap<>();
                    try (Cursor cursor = AppContext.getAppContext().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, null, null, null)) {
                        while (cursor.moveToNext()) {

                            int i_name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                            String displayName = "";
                            if (i_name != -1) {
                                displayName = cursor.getString(i_name);
                            }

                            int i_number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String number = "";
                            if (i_number != -1) {
                                number = cursor.getString(i_number);
                            }

                            number = StringUtils.trim(number);
                            //删除掉号码中可能夹带的分隔符
                            number = number.replaceAll("_", "");
                            number = number.replaceAll("/", "");
                            number = number.replaceAll("-", "");
                            number = number.replaceAll("&", "");
                            number = number.replaceAll("\\.", "");
                            map.put(displayName, number);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    emitter.onNext(map);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(observer);
    }
}
