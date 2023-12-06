package com.ipusoft.context.db;

import androidx.room.Room;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.db.dao.CallTaskDao;
import com.ipusoft.context.db.dao.CallTaskDetailsDao;
import com.ipusoft.context.db.dao.SysRecordingDao;
import com.ipusoft.context.db.update.Migration1To2;
import com.ipusoft.context.db.update.Migration4To5;

/**
 * author : GWFan
 * time   : 5/28/21 10:47 AM
 * desc   :
 */

public class AppDBManager {
    private static final String TAG = "DBManager";
    private static AppDatabase appDB;

    public static void initDataBase() {
        appDB = Room.databaseBuilder(AppContext.getAppContext(), AppDatabase.class,
                        "ipusdk.db")
                .fallbackToDestructiveMigration()
                .addMigrations(new Migration1To2(1, 2), new Migration4To5(4, 5))
                .build();
    }

    /**
     * 清空所有数据库表
     */
    public static void clearAllTables() {
        if (appDB != null) {
            new Thread(() -> appDB.clearAllTables()).start();
        }
    }

    public static SysRecordingDao getSysRecordingDao() {
        return appDB.sysRecordingDao();
    }

    public static CallTaskDao getCallTaskDao() {
        return appDB.callTaskDao();
    }

    public static CallTaskDetailsDao getCallTaskDetailsDao() {
        return appDB.callTaskDetailsDao();
    }
}
