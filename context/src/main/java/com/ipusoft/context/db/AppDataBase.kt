package com.ipusoft.context.db;

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ipusoft.context.bean.SysRecording
import com.ipusoft.context.db.dao.SysRecordingDao

/**
 * author : GWFan
 * time   : 9/2/20 6:00 PM
 * desc   :
 */

@Database(entities = [SysRecording::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sysRecordingDao(): SysRecordingDao
}