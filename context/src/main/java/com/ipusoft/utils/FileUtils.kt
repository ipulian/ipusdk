package com.ipusoft.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.ipusoft.context.IpuSoftSDK
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat

/**
 * author : GWFan
 * time   : 2020/4/17 11:37
 * desc   :
 */

private const val APK_DIRECTORY = "/apk"
private const val LOG_DIRECTORY = "/logs"
private const val LOG_TEMP_DIRECTORY = "/logs/temp"
private const val LOG_ERROR_DIRECTORY = "/logs/error"
private const val JSON_DIRECTORY = "/json"
private const val AUDIO_DIRECTORY = "/audio"
private const val THUMBNAIL_DIRECTORY = "/thumbnails"
const val AREA_JSON_FILE = "area.json"


private fun getFilePath(context: Context): String? {
    return if (SDCardUtils.isSDCardEnableByEnvironment() || !Environment.isExternalStorageRemovable()) {
        context.getExternalFilesDir(null)!!.path
    } else {
        context.filesDir.path
    }
}

fun formatFileSize(fileS: Long): String {
    val df = DecimalFormat("#")
    var fileSizeString = ""
    fileSizeString = when {
        fileS == 0L -> {
            df.format(fileS.toDouble()).toString() + "KB"
        }
        fileS < 1024 -> {
            df.format(fileS.toDouble()).toString() + "B"
        }
        fileS < 1048576 -> {
            df.format(fileS.toDouble() / 1024).toString() + "KB"
        }
        fileS < 1073741824 -> {
            df.format(fileS.toDouble() / 1048576).toString() + "MB"
        }
        else -> {
            df.format(fileS.toDouble() / 1073741824).toString() + "GB"
        }
    }
    return fileSizeString
}

fun getFileMD5ToString(file: File?): String {
    return StringUtils.bytes2HexString(getFileMD5(file), true)
}

private fun getFileMD5(file: File?): ByteArray? {
    if (file == null) return null
    var dis: DigestInputStream? = null
    try {
        val fis = FileInputStream(file)
        var md = MessageDigest.getInstance("MD5")
        dis = DigestInputStream(fis, md)
        val buffer = ByteArray(1024 * 256)
        while (true) {
            if (dis.read(buffer) <= 0) break
        }
        md = dis.messageDigest
        return md.digest()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            dis?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return null
}

/**
 * Create a directory if it doesn't exist, otherwise do nothing.
 *
 * @param file The file.
 * @return `true`: exists or creates successfully<br></br>`false`: otherwise
 */
fun createOrExistsDir(file: File?): Boolean {
    return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
}

fun getFileExtension(file: File?): String? {
    return if (file == null) "" else getFileExtension(file.path)
}

/**
 * Return the extension of file.
 *
 * @param filePath The path of file.
 * @return the extension of file
 */
fun getFileExtension(filePath: String): String? {
    if (StringUtils.isSpace(filePath)) return ""
    val lastPoi = filePath.lastIndexOf('.')
    val lastSep = filePath.lastIndexOf(File.separator)
    return if (lastPoi == -1 || lastSep >= lastPoi) "" else filePath.substring(lastPoi + 1)
}

fun isFileExists(file: File?): Boolean {
    if (file == null) return false
    return if (file.exists()) {
        true
    } else isFileExists(file.absolutePath)
}

fun createOrExistsFile(file: File?): Boolean {
    if (file == null) return false
    if (file.exists()) return file.isFile
    return if (!createOrExistsDir(file.parentFile)) false else try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}


/**
 * Return whether the file exists.
 *
 * @param filePath The path of file.
 * @return `true`: yes<br></br>`false`: no
 */
fun isFileExists(filePath: String): Boolean {
    val file = File(filePath)
    return if (file.exists()) {
        true
    } else isFileExistsApi29(filePath)
}

private fun isFileExistsApi29(filePath: String): Boolean {
    if (Build.VERSION.SDK_INT >= 29) {
        try {
            val uri = Uri.parse(filePath)
            val cr = IpuSoftSDK.getAppContext().contentResolver
            val afd = cr.openAssetFileDescriptor(uri, "r") ?: return false
            try {
                afd.close()
            } catch (ignore: IOException) {
            }
        } catch (e: FileNotFoundException) {
            return false
        }
        return true
    }
    return false
}

/**
 * 获取日志文件存储路径
 *
 * @param context
 * @return
 */
fun getLogPath(context: Context): String {
    val logPath: String = getFilePath(context) + LOG_DIRECTORY
    val file = File(logPath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return logPath
}

fun getLogTempPath(context: Context): String {
    val logTempPath: String = getFilePath(context) + LOG_TEMP_DIRECTORY
    val file = File(logTempPath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return logTempPath
}

fun getLogErrorPath(context: Context): String {
    val logPath: String = getFilePath(context) + LOG_ERROR_DIRECTORY
    val file = File(logPath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return logPath
}

/**
 * 获取Json文件存储路径
 *
 * @param context
 * @return
 */
fun getJsonPath(context: Context): String {
    val logPath: String = getFilePath(context) + JSON_DIRECTORY
    val file = File(logPath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return logPath
}

/**
 * 获取缩略图存储路径
 */
fun getThumbnailPath(context: Context): String {
    val thumbnailPath: String = getFilePath(context) + THUMBNAIL_DIRECTORY
    val file = File(thumbnailPath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return thumbnailPath
}

/**
 * 获取apk存储路径
 */
fun getApkPath(context: Context): String {
    val apkPath: String = getFilePath(context) + APK_DIRECTORY
    val file = File(apkPath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return apkPath
}

/**
 * 获取音频文件存储路径
 *
 * @param context
 * @return
 */
fun getAudioPath(context: Context): String {
    val audioPath: String = getFilePath(context) + AUDIO_DIRECTORY
    val file = File(audioPath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return audioPath
}

interface OnReplaceListener {
    fun onReplace(srcFile: File?, destFile: File?): Boolean
}

fun copy(src: File, dest: File): Boolean {
    return copy(src, dest, null)
}

fun copy(src: File, dest: File, listener: OnReplaceListener?): Boolean {
    if (src == null) return false
    return if (src.isDirectory) {
        copyDir(src, dest, listener)
    } else copyFile(src, dest, listener)
}

private fun copyDir(srcDir: File, destDir: File, listener: OnReplaceListener?): Boolean {
    return copyOrMoveDir(srcDir, destDir, listener, false)
}

/**
 * Copy the file.
 *
 * @param srcFile  The source file.
 * @param destFile The destination file.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
private fun copyFile(srcFile: File, destFile: File, listener: OnReplaceListener?): Boolean {
    return copyOrMoveFile(srcFile, destFile, listener, false)
}

private fun copyOrMoveDir(srcDir: File, destDir: File, listener: OnReplaceListener?, isMove: Boolean): Boolean {
    if (srcDir == null || destDir == null) return false
    // destDir's path locate in srcDir's path then return false
    val srcPath = srcDir.path + File.separator
    val destPath = destDir.path + File.separator
    if (destPath.contains(srcPath)) return false
    if (!srcDir.exists() || !srcDir.isDirectory) return false
    if (!createOrExistsDir(destDir)) return false
    val files = srcDir.listFiles()
    if (files != null && files.size > 0) {
        for (file in files) {
            val oneDestFile = File(destPath + file.name)
            if (file.isFile) {
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false
            } else if (file.isDirectory) {
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false
            }
        }
    }
    return !isMove || deleteDir(srcDir)
}


private fun copyOrMoveFile(srcFile: File, destFile: File, listener: OnReplaceListener?, isMove: Boolean): Boolean {
    if (srcFile == null || destFile == null) return false
    // srcFile equals destFile then return false
    if (srcFile == destFile) return false
    // srcFile doesn't exist or isn't a file then return false
    if (!srcFile.exists() || !srcFile.isFile) return false
    if (destFile.exists()) {
        if (listener == null || listener.onReplace(srcFile, destFile)) { // require delete the old file
            if (!destFile.delete()) { // unsuccessfully delete then return false
                return false
            }
        } else {
            return true
        }
    }
    return if (!createOrExistsDir(destFile.parentFile)) false else try {
        (FileIOUtils.writeFileFromIS(destFile.absolutePath, FileInputStream(srcFile))
                && !(isMove && deleteFile(srcFile)))
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        false
    }
}

private fun deleteDir(dir: File?): Boolean {
    if (dir == null) return false
    // dir doesn't exist then return true
    if (!dir.exists()) return true
    // dir isn't a directory then return false
    if (!dir.isDirectory) return false
    val files = dir.listFiles()
    if (files != null && files.isNotEmpty()) {
        for (file in files) {
            if (file.isFile) {
                if (!file.delete()) return false
            } else if (file.isDirectory) {
                if (!deleteDir(file)) return false
            }
        }
    }
    return dir.delete()
}

private fun deleteFile(file: File?): Boolean {
    return file != null && (!file.exists() || file.isFile && file.delete())
}

/**
 * 判断文件是否存在
 */
fun fileIsExists(filePath: String): Boolean {
    val sDCardAvailable: Boolean = SDCardUtils.isSDCardEnableByEnvironment()
    if (!sDCardAvailable) {
        return false
    }
    try {
        val f = File(filePath)
        if (!f.exists()) {
            return false
        }
    } catch (e: Exception) {
        return false
    }
    return true
}

fun getFileLength(file: File?): Long {
    return if (file == null || !file.exists()) {
        0
    } else file.length()
}
