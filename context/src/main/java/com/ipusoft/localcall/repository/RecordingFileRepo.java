package com.ipusoft.localcall.repository;

import android.os.Environment;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.bean.LocalRecordPath;
import com.ipusoft.context.iface.OnMyValueClickListener;
import com.ipusoft.context.manager.ThreadPoolManager;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.constant.AudioExpandedName;
import com.ipusoft.localcall.datastore.SimDataRepo;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.utils.SysRecordingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void queryRecordingFile(Map<String, String> namePhoneMap, OnMyValueClickListener<List<File>> listener) {
        XLog.d(TAG + "->queryRecordingFile开始查找录音文件");
//        Observable.create((ObservableOnSubscribe<List<File>>) emitter
//                        -> emitter.onNext(
        ThreadPoolManager.newInstance().addExecuteTask(() -> {
            List<File> list = queryRecordingFile(0, 0, namePhoneMap);
            if (listener != null) {
                listener.onMyValueCallBack(list);
            }
        });
//        ))
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(observer);
    }

    public List<File> queryRecordingFile(long beginTime) {
        return queryRecordingFile(1, beginTime, null);
    }

    public List<File> queryRecordingFile(int isAll, long beginTime, Map<String, String> namePhoneMap) {

        ArrayList<File> list = new ArrayList<>();
        File tempFile = null;

        //XLog.d(TAG + "->isSDCardEnableByEnvironment？" + SDCardUtils.isSDCardEnableByEnvironment());
        // XLog.d(TAG + "->isExternalStorageRemovable？" + Environment.isExternalStorageRemovable());
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//系统版本 >= Android 11
        //    XLog.d(TAG + "->checkManageStoragePermission？" + Environment.isExternalStorageManager());
        // }
        if (SysRecordingUtils.isMIUI()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/MIUI/sound_recorder/call_rec");
            XLog.d(TAG + "->小米手机->默认路径是否存在？" + tempFile.exists());
        } else if (SysRecordingUtils.isHUAWEI()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/sounds/callrecord");
            XLog.d(TAG + "->华为手机->默认路径是否存在？" + tempFile.exists());
        } else if (SysRecordingUtils.isVIVO()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/Record/Call");
            XLog.d(TAG + "->VIVO手机->默认路径是否存在？" + tempFile.exists());
        } else if (SysRecordingUtils.isOPPO()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/Music/Recordings/Call Recordings");
            XLog.d(TAG + "->OPPO手机->默认路径是否存在？" + tempFile.exists());
        } else if (SysRecordingUtils.isRealme()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/Music/Recordings/Call Recordings");
            XLog.d(TAG + "->realme手机->默认路径是否存在？" + tempFile.exists());
        } else if (SysRecordingUtils.isMeizu()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/Recorder/call");
            XLog.d(TAG + "->魅族手机->默认路径是否存在？" + tempFile.exists());
        } else if (SysRecordingUtils.isOnePlus()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/Record/PhoneRecord");
            XLog.d(TAG + "->OnePlus手机->默认路径是否存在？" + tempFile.exists());
        } else if (SysRecordingUtils.isMotorola()) {
            tempFile = new File(Environment.getExternalStorageDirectory() + "/Music/Sound_recorder/Phone_recorder");
            XLog.d(TAG + "->摩托罗拉手机->默认路径是否存在？" + tempFile.exists());
        }

        if (tempFile != null && tempFile.exists() && tempFile.isDirectory()) {
            File[] children = tempFile.listFiles();
            if (children != null) {
                if (SysRecordingUtils.isOnePlus()) {//OnePlus手机特殊处理
                    if (isAll == 1) {
                        for (File f : children) {
                            if (f != null && f.exists() && f.isDirectory()) {
                                File[] c2 = f.listFiles();
                                if (c2 != null) {
                                    for (File f2 : c2) {
                                        if (judgeRecordFile(f2) && judgeFileContainsNumber(f2, namePhoneMap)
                                                && judgeFolderIsLegal(f2) && judgeFileSizeIsLegal(f2) && judgeFileLastModifyTime2(f2, beginTime)) {
                                            list.add(f2);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        for (File f : children) {
                            if (f != null && f.exists() && f.isDirectory()) {
                                File[] c2 = f.listFiles();
                                if (c2 != null) {
                                    for (File f2 : c2) {
                                        if (judgeRecordFile(f2) && judgeFileContainsNumber(f2, namePhoneMap)
                                                && judgeFolderIsLegal(f2) && judgeFileSizeIsLegal(f2) && judgeFileLastModifyTime(f2)) {
                                            list.add(f2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (SysRecordingUtils.isMeizu()) {//魅族
                    if (isAll == 1) {
                        for (File f : children) {
                            if (f != null && f.exists() && f.isFile() && judgeRecordFile(f)
                                    && judgeFolderIsLegal(f) && judgeFileSizeIsLegal(f) && judgeFileLastModifyTime2(f, beginTime)) {
                                list.add(f);
                            }
                        }
                    } else {
                        Log.d(TAG, "queryRecordingFile: ---------2->");
                        for (File f : children) {
                            if (f != null && f.exists() && f.isFile() && judgeRecordFile(f)
                                    && judgeFolderIsLegal(f) && judgeFileSizeIsLegal(f) && judgeFileLastModifyTime(f)) {
                                list.add(f);
                            }
                        }
                    }
                }
                if (ArrayUtils.isEmpty(list)) {
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
        }

        XLog.d(TAG + "-----通过默认路径查找录音-------->>\n" + GsonUtils.toJson(list));

        if (ArrayUtils.isEmpty(list)) {
            String sysAudioPath = "";
            List<LocalRecordPath> recordPathList = CommonDataRepo.getLocalRecordPath();
            if (ArrayUtils.isNotEmpty(recordPathList)) {
                XLog.d(TAG + "->recordPathList--->\n" + GsonUtils.toJson(recordPathList));
                int maxWeight = 0;
                for (LocalRecordPath localRecordPath : recordPathList) {
                    if (maxWeight < localRecordPath.getWeight()) {
                        sysAudioPath = localRecordPath.getPath();
                        maxWeight = localRecordPath.getWeight();
                    }
                }
            }

            if (StringUtils.isNotEmpty(sysAudioPath)) {
                XLog.d(TAG + "--------sysAudioPath----->" + sysAudioPath);
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
        }
        XLog.d(TAG + "------------>根据系统路径查找到的----->\n" + GsonUtils.toJson(list));
        if (ArrayUtils.isEmpty(list)) {
            Stack<Dir> stack = new Stack<>();
            Dir root = new Dir(Environment.getExternalStorageDirectory());
            stack.push(root);                 //将根目录压入栈中
            XLog.d("最后一种查找录音的方式，全局遍历---->start");
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
            XLog.d("最后一种查找录音的方式，全局遍历---->end");
        }
        if (ArrayUtils.isNotEmpty(list)) {
            try {
                XLog.d("最后一种查找录音的方式，全局遍历----查找结果>\n" + GsonUtils.toJson(list));
//                for (File file : list) {
                //  XLog.d(TAG + "->queryRecordingFile->" + file.getAbsolutePath() );
                //     }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            XLog.d(TAG + "->queryRecordingFile->null1");

            XLog.d(TAG + "->queryRecordingFile->全局遍历，仍然没有找到录音，尝试扩大匹配范围，再次查找");
            Stack<Dir> stack = new Stack<>();
            Dir root = new Dir(Environment.getExternalStorageDirectory());
            stack.push(root);                 //将根目录压入栈中
            XLog.d("最后一种查找录音的方式，全局遍历---->start");
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
                                if (file.exists() && file.isFile() && judgeRecordFile(file)
                                        && judgeFolderIsLegal(file) && judgeFileSizeIsLegal(file) && judgeFileLastModifyTime2(file, beginTime)) {
                                    list.add(file);
                                }
                            } else {
                                if (file.exists() && file.isFile() && judgeRecordFile(file)
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
        //部分手机（魅族），录音文件中不包含号码或姓名
        if (SysRecordingUtils.isMeizu()) {
            isRecordFile = true;
        } else {
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
            Pattern pattern = Pattern.compile("((1[3-9])\\d{9})|((0[1-9])\\d{7,11})|((0[1-9][0-9]-)\\d{7,9})|((0[1-9][0-9][0-9]-)\\d{7,9})");
            Matcher matcher = pattern.matcher(str);
            hasPNum = matcher.find();
        }
        return hasPNum;
    }

    public static void main(String[] args) {
        String str = "03943391669_20230621104122";
        System.out.println(judgeHasPNum(str));
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