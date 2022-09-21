package com.ipusoft.localcall.repository;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.bean.LocalRecordPath;
import com.ipusoft.context.manager.PlatformManager;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.constant.AudioExpandedName;
import com.ipusoft.localcall.datastore.SimDataRepo;
import com.ipusoft.logger.XLogger;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.permission.RxPermissionUtils;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.SDCardUtils;
import com.ipusoft.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author : GWFan
 * time   : 4/29/21 9:20 AM
 * desc   : 系统通话录音
 */

public class RecordingFileRepo {
    private static final String TAG = "RecordingFileRepo";

    private static class RecordingFileRepoHolder {
        private static final RecordingFileRepo INSTANCE = new RecordingFileRepo();
    }

    public static RecordingFileRepo getInstance() {
        return RecordingFileRepoHolder.INSTANCE;
    }

    public void queryRecordingFile(Map<String, String> namePhoneMap, Observer<List<File>> observer) {
        XLogger.d(TAG + "->queryRecordingFile开始查找录音文件");
        Observable.create((ObservableOnSubscribe<List<File>>) emitter
                        -> emitter.onNext(queryRecordingFile(0, 0, namePhoneMap)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public List<File> queryRecordingFile(long beginTime) {
        return queryRecordingFile(1, beginTime, null);
    }

    public List<File> queryRecordingFile(int isAll, long beginTime, Map<String, String> namePhoneMap) {

        ArrayList<File> list = new ArrayList<>();
        File tempFile = null;

        XLogger.d(TAG + "->isSDCardEnableByEnvironment？" + SDCardUtils.isSDCardEnableByEnvironment());
        XLogger.d(TAG + "->isExternalStorageRemovable？" + Environment.isExternalStorageRemovable());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//系统版本 >= Android 11
            XLogger.d(TAG + "->checkManageStoragePermission？" + RxPermissionUtils.checkManageStoragePermission());
        }
        if (PlatformManager.isMIUI()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/MIUI/sound_recorder/call_rec");
            XLogger.d(TAG + "->小米手机->默认路径是否存在？" + tempFile.exists());
        } else if (PlatformManager.isHUAWEI()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/sounds/callrecord");
            XLogger.d(TAG + "->华为手机->默认路径是否存在？" + tempFile.exists());
        }

        if (tempFile != null && tempFile.exists() && tempFile.isDirectory()) {
            File[] children = tempFile.listFiles();
            if (children != null) {
                Log.d(TAG, "queryRecordingFile: ---------1->");
                if (isAll == 1) {
                    for (File f : children) {
                        if (f != null && f.exists() && f.isFile() && judgeRecordFile(f) && judgeFileContainsNumber(f, namePhoneMap)
                                && judgeFolderIsLegal(f) && judgeFileSizeIsLegal(f) && judgeFileLastModifyTime2(f, beginTime)) {
                            list.add(f);
                        }
                    }
                } else {
                    Log.d(TAG, "queryRecordingFile: ---------2->");
                    for (File f : children) {
                        if (f != null && f.exists() && f.isFile() && judgeRecordFile(f) && judgeFileContainsNumber(f, namePhoneMap)
                                && judgeFolderIsLegal(f) && judgeFileSizeIsLegal(f) && judgeFileLastModifyTime(f)) {
                            list.add(f);
                        }
                    }
                }
            }
        }
        Log.d(TAG, "queryRecordingFile: ----------->" + GsonUtils.toJson(list));

        if (ArrayUtils.isEmpty(list)) {
            String sysAudioPath = "";
            List<LocalRecordPath> recordPathList = CommonDataRepo.getLocalRecordPath();
            if (ArrayUtils.isNotEmpty(recordPathList)) {
                XLogger.d(TAG + "->recordPathList--->" + GsonUtils.toJson(recordPathList));
                int maxWeight = 0;
                for (LocalRecordPath localRecordPath : recordPathList) {
                    if (maxWeight < localRecordPath.getWeight()) {
                        sysAudioPath = localRecordPath.getPath();
                        maxWeight = localRecordPath.getWeight();
                    }
                }
            }

            if (StringUtils.isNotEmpty(sysAudioPath)) {
                XLogger.d(TAG + "->sysAudioPath--->" + sysAudioPath);
                File temp = new File(sysAudioPath);
                if (temp.exists() && temp.isDirectory()) {
                    File[] children = temp.listFiles();
                    if (children != null) {
                        if (isAll == 1) {
                            for (File f : children) {
                                if (f != null && f.exists() && f.isFile() && judgeRecordFile(f) && judgeFileContainsNumber(f, namePhoneMap)
                                        && judgeFolderIsLegal(f) && judgeFileSizeIsLegal(f) && judgeFileLastModifyTime2(f, beginTime)) {
                                    list.add(f);
                                }
                            }
                        } else {
                            for (File f : children) {
                                if (f != null && f.exists() && f.isFile() && judgeRecordFile(f) && judgeFileContainsNumber(f, namePhoneMap)
                                        && judgeFolderIsLegal(f) && judgeFileSizeIsLegal(f) && judgeFileLastModifyTime(f)
                                ) {
                                    list.add(f);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            XLogger.d(TAG + "->list1->" + list.size());
        }

        if (ArrayUtils.isEmpty(list)) {
            Stack<Dir> stack = new Stack<>();
            Dir root = new Dir(Environment.getExternalStorageDirectory());
            stack.push(root);                 //将根目录压入栈中
            while (!stack.empty()) {            //循环处理栈顶目录
                Dir currentDir = stack.peek();//获取栈顶元素,只取值,不出栈
                if (currentDir.peekNum > 0) {         //若栈顶目录之前peek过，则略过该目录(因为其子文件已全部被处理过)
                    stack.pop();
                    continue;
                }
                File[] children = currentDir.dir.listFiles();
                if (children == null || children.length == 0) {   //若该目录是空目录，则略过该目录
                    stack.pop();
                } else {                                          //定义统计子目录数的变量
                    int countDir = 0;
                    for (File file : children) {
                        if (file.isDirectory()) {
                            stack.push(new Dir(file));       //对于子目录，不处理，直接压入栈中，然后重新执行循环
                            countDir++;
                        } else {
                            if (isAll == 1) {
                                if (file.exists() && file.isFile() && judgeRecordFile(file) && judgeFileContainsNumber(file, namePhoneMap)
                                        && judgeFolderIsLegal(file) && judgeFileSizeIsLegal(file) && judgeFileLastModifyTime2(file, beginTime)) {
                                    list.add(file);
                                }
                            } else {
                                if (file.exists() && file.isFile() && judgeRecordFile(file) && judgeFileContainsNumber(file, namePhoneMap)
                                        && judgeFolderIsLegal(file) && judgeFileSizeIsLegal(file) && judgeFileLastModifyTime(file)) {
                                    list.add(file);
                                }
                            }
                        }
                    }
                    if (countDir == 0) {                          //如果子目录数为0，则该目录是叶子节点，将其剔出栈
                        stack.pop();
                    } else {                                      //如果子目录数不为0，则标示下该目录已经被peek过
                        currentDir.peekNum++;
                    }
                }
            }
        } else {
            XLogger.d(TAG + "->list2->" + list.size());
        }
        if (ArrayUtils.isNotEmpty(list)) {
            try {
                for (File file : list) {
                    XLogger.d(TAG + "->queryRecordingFile->" + file.getAbsolutePath() + "->" + file.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            XLogger.d(TAG + "->queryRecordingFile->null1");
        }
        return list;
    }

    /**
     * 判断是否是录音文件
     *
     * @return
     */
    private boolean judgeRecordFile(File file) {
        boolean isRecordFile = false;
        if (file != null) {
            if (AudioExpandedName.judgeExpandedName(file)) {
                isRecordFile = true;
            }
        }
        return isRecordFile;
    }

    /**
     * 判断文件中是否包含电话号码
     *
     * @return
     */
    private boolean judgeFileContainsNumber(File file, Map<String, String> namePhoneMap) {
        boolean isRecordFile = false;
        if (file != null && !file.isDirectory()) {
            String fileName = file.getName();
            if (judgeHasPNum(fileName)) {
                isRecordFile = true;
            }
            //文件名不包含手机号，判断是否包含通讯录中的姓名
            if (!isRecordFile && namePhoneMap != null && namePhoneMap.size() != 0) {
                String substring = fileName.substring(0, fileName.lastIndexOf("."));
                for (Map.Entry<String, String> entry : namePhoneMap.entrySet()) {
                    if (substring.contains(entry.getKey())) {
                        isRecordFile = true;
                        break;
                    }
                }
            }
        }
        return isRecordFile;
    }

    /**
     * 判断字符串是否包含电话号码
     *
     * @param str
     * @return
     */
    private static boolean judgeHasPNum(String str) {
        boolean hasPNum = false;
        if (StringUtils.isNotEmpty(str)) {
            Pattern pattern = Pattern.compile("((1[3-9])\\d{9})|((0[1-9])\\d{7,9})|((0[1-9][0-9]-)\\d{7,9})|((0[1-9][0-9][0-9]-)\\d{7,9})");
            Matcher matcher = pattern.matcher(str);
            hasPNum = matcher.find();
        }
        return hasPNum;
    }

//    public static void main(String[] args) {
//        String str = "138 1024 5310_2021110414141445";
//        System.out.println(judgeHasPNum(str));
//    }

    /**
     * 判断文件时间是否合理
     *
     * @param file
     * @return
     */
    private boolean judgeFileLastModifyTime(File file) {
        boolean isRecordFile = false;
        if (file != null && !file.isDirectory()) {
            long l = file.lastModified();
            UploadSysRecordingBean uploadSysCallLog = SimDataRepo.getUploadSysCallLog();
            long timestamp = uploadSysCallLog.getTimestamp();
            if (l >= timestamp) {
                isRecordFile = true;
            }
        }
        return isRecordFile;
    }

    private boolean judgeFileLastModifyTime2(File file, long beginTime) {
        boolean isRecordFile = false;
        if (file != null && !file.isDirectory()) {
            long l = file.lastModified();
            // UploadSysRecordingBean uploadSysCallLog = SimDataRepo.getUploadSysCallLog();
            // long timestamp = uploadSysCallLog.getTimestamp();
            if (l >= beginTime) {
                isRecordFile = true;
            }
        }
        return isRecordFile;
    }

    /**
     * 判断文件大小是否合理
     *
     * @param file
     * @return
     */
    private boolean judgeFileSizeIsLegal(File file) {
//        boolean isRecordFile = false;
//        if (file != null && !file.isDirectory()) {
//            long fileLength = file.length();
//            if (fileLength < Constant.MAX_FILE_SIZE) {
//                isRecordFile = true;
//            }
//        }
        return true;
    }

    /**
     * 判断文件所在文件夹是否合理(非录音文件的副本，也不是以.开头的隐藏文件)
     *
     * @return
     */
    private boolean judgeFolderIsLegal(File file) {
        boolean isRecordFile = true;
        String[] list = file.getAbsolutePath().split("/");
        if (!StringUtils.equals(file.getParent(), FileUtilsKt.getAudioPath(AppContext.getAppContext()))) {
            for (String str : list) {
                if (str.startsWith(".")) {
                    isRecordFile = false;
                    break;
                }
            }
        } else {
            isRecordFile = false;
        }
        return isRecordFile;
    }

    static class Dir {
        File dir;
        int peekNum;

        Dir(File dir) {
            this.dir = dir;
            this.peekNum = 0;
        }
    }
}