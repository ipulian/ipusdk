package com.ipusoft.context.utils

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

/**
 * 判断文件是否存在
 */
fun fileIsExists(filePath: String): Boolean {
    val sDCardAvailable: Boolean = (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState())
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

fun formatFileSize(fileS: Long): String {
    val df = DecimalFormat("#.0")
    var fileSizeString = ""
    fileSizeString = when {
        fileS < 1024 -> {
            df.format(fileS.toDouble()).toString() + "B"
        }
        fileS < 1048576 -> {
            df.format(fileS.toDouble() / 1024).toString() + "K"
        }
        fileS < 1073741824 -> {
            df.format(fileS.toDouble() / 1048576).toString() + "M"
        }
        else -> {
            df.format(fileS.toDouble() / 1073741824).toString() + "G"
        }
    }
    return fileSizeString
}

fun getFileMD5ToString(file: File?): String {
    return StringUtils.bytes2HexString(getFileMD5(file), true)
}

fun getFileMD5(file: File?): ByteArray? {
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

fun isFile(file: File?): Boolean {
    return file != null && file.exists() && file.isFile
}

fun isFileExists(file: File?): Boolean {
    if (file == null) return false
    return if (file.exists()) {
        true
    } else isFileExists(file.absolutePath)
}

fun getFileLength(file: File?): Long {
    return if (file == null || !file.exists()) {
        0
    } else file.length()
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