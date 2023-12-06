package com.ipusoft.context.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ipusoft.context.bean.CallTask;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * author : GWFan
 * time   : 4/28/21 11:35 AM
 * desc   :
 */

@Dao
public interface CallTaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<CallTask> taskList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CallTask task);

    @Query("SELECT * FROM call_task WHERE deleted != 1 AND status in (:statusList) ORDER BY extend4 desc, id desc LIMIT 200")
    Observable<List<CallTask>> queryAll(List<Integer> statusList);

    @Query("SELECT * FROM call_task WHERE deleted != 1 AND total != executed ORDER BY extend4 desc, id desc LIMIT 200")
    Observable<List<CallTask>> queryRunning();

    @Query("SELECT * FROM call_task WHERE deleted != 1 AND total = executed ORDER BY extend4 desc, id desc LIMIT 200")
    Observable<List<CallTask>> queryFinished();

    @Query("SELECT * FROM call_task WHERE deleted != 1 AND total != executed"
            + " AND extend4 >= :startTime AND :endTime >= extend4 ORDER BY ctime LIMIT 200")
    Observable<List<CallTask>> queryRunningByExecutedTime(String startTime, String endTime);

    @Query("UPDATE call_task SET executed= :executed WHERE id = :taskId")
    void updateExecutedById(long taskId, int executed);

    @Query("UPDATE call_task SET total= total + (:count) WHERE id = :taskId")
    void updateAutoTotalById(long taskId, int count);

    @Query("UPDATE call_task SET executed= executed + (:count) WHERE id = :taskId")
    void updateAutoExecutedById(long taskId, int count);

    @Query("UPDATE call_task SET executed= :executed, status =:status WHERE id = :taskId")
    void updateExecutedById(long taskId, int executed, int status);
}
