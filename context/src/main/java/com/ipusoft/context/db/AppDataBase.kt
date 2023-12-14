package com.ipusoft.context.db;

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ipusoft.context.bean.CallTask
import com.ipusoft.context.bean.CallTaskDetails
import com.ipusoft.context.bean.SysRecording
import com.ipusoft.context.db.dao.CallTaskDao
import com.ipusoft.context.db.dao.CallTaskDetailsDao
import com.ipusoft.context.db.dao.SysRecordingDao

/**
 * author : GWFan
 * time   : 9/2/20 6:00 PM
 * desc   :
 */

@Database(entities = [SysRecording::class, CallTask::class, CallTaskDetails::class], version = 7)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sysRecordingDao(): SysRecordingDao

    abstract fun callTaskDao(): CallTaskDao

    abstract fun callTaskDetailsDao(): CallTaskDetailsDao
}