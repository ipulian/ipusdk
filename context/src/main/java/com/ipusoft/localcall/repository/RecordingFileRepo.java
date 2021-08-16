package com.ipusoft.localcall.repository;

import android.os.Environment;

import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.constant.AudioExpandedName;
import com.ipusoft.localcall.constant.Constant;
import com.ipusoft.localcall.datastore.SimDataRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    private static final String TAG = "RecordingFileRepository";

    private static class RecordingFileRepoHolder {
        private static final RecordingFileRepo INSTANCE = new RecordingFileRepo();
    }

    public static RecordingFileRepo getInstance() {
        return RecordingFileRepoHolder.INSTANCE;
    }

    public void queryRecordingFile(Observer<List<File>> observer) {
        Observable.create((ObservableOnSubscribe<List<File>>) emitter
                -> emitter.onNext(queryRecordingFile()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public List<File> queryRecordingFile() {
        ArrayList<File> list = new ArrayList<>();
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
                        if (judgeRecordFile(file) && judgeFileContainsNumber(file)
                                && judgeFolderIsLegal(file) && judgeFileSizeIsLegal(file)
                                && judgeFileLastModifyTime(file)) {
                            list.add(file);
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
    private boolean judgeFileContainsNumber(File file) {
        boolean isRecordFile = false;
        if (file != null && !file.isDirectory()) {
            String fileName = file.getName();
            if (judgeHasPNum(fileName)) {
                isRecordFile = true;
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
    private boolean judgeHasPNum(String str) {
        boolean hasPNum = false;
        if (StringUtils.isNotEmpty(str)) {
            Pattern pattern = Pattern.compile("((1[3-9])\\d{9})|((0[1-9])\\d{7,9})|((0[1-9][0-9]-)\\d{7,9})|((0[1-9][0-9][0-9]-)\\d{7,9})");
            Matcher matcher = pattern.matcher(str);
            hasPNum = matcher.find();
        }
        return hasPNum;
    }

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

    /**
     * 判断文件大小是否合理
     *
     * @param file
     * @return
     */
    private boolean judgeFileSizeIsLegal(File file) {
        boolean isRecordFile = false;
        if (file != null && !file.isDirectory()) {
            long fileLength = file.length();
            if (fileLength < Constant.MAX_FILE_SIZE) {
                isRecordFile = true;
            }
        }
        return isRecordFile;
    }

    /**
     * 判断文件所在文件夹是否合理(非录音文件的副本，也不是以.开头的隐藏文件)
     *
     * @return
     */
    private boolean judgeFolderIsLegal(File file) {
        boolean isRecordFile = true;
        String[] list = file.getAbsolutePath().split("/");
        if (!StringUtils.equals(file.getParent(),
                FileUtilsKt.getAudioPath(IpuSoftSDK.getAppContext()))) {
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
