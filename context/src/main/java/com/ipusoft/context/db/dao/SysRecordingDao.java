package com.ipusoft.context.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ipusoft.context.bean.SysRecording;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * author : GWFan
 * time   : 4/28/21 11:35 AM
 * desc   :
 */

@Dao
public interface SysRecordingDao {

    @Query("SELECT * FROM sys_recording WHERE call_id in (:callId) LIMIT 1")
    SysRecording queryByCallId(long callId);

    @Query("SELECT * FROM sys_recording WHERE upload_status in (:uploadStatus) ORDER BY call_time ")
    List<SysRecording> queryAll(List<Integer> uploadStatus);

    @Query("SELECT * FROM sys_recording WHERE upload_status in (:uploadStatus) ORDER BY call_time DESC LIMIT :limit")
    Observable<List<SysRecording>> queryLimitRecordingByStatus(List<Integer> uploadStatus, int limit);

    @Query("SELECT * FROM sys_recording WHERE upload_status in (:uploadStatus) ORDER BY call_time DESC LIMIT :offset, :pageSize")
    Observable<List<SysRecording>> queryLimitRecordingByStatus(List<Integer> uploadStatus, int offset, int pageSize);

    @Query("SELECT count(*) as count FROM sys_recording WHERE upload_status in (:uploadStatus)")
    Observable<Integer> queryCountByStatus(List<Integer> uploadStatus);

    @Delete
    void deleteRecording(SysRecording... recording);

    @Query("DELETE FROM sys_recording WHERE call_time in (:list)")
    void deleteRecording(List<Long> list);

    @Query("DELETE FROM sys_recording WHERE upload_status = :status")
    void deleteRecording(int status);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<SysRecording> recordList);

    @Query("SELECT * FROM sys_recording WHERE upload_status in (:uploadStatus) "
            + "AND retry_count <= :retryCount AND :currentTime - last_retry_time > 10*60*1000 "
            + "ORDER BY id DESC LIMIT :limit")
    Observable<List<SysRecording>> queryLimitRecordingByStatus(List<Integer> uploadStatus, int retryCount,
                                                               long currentTime, int limit);

    @Query("SELECT * FROM sys_recording WHERE upload_status in (:uploadStatus) "
            + "AND retry_count <= :retryCount AND :currentTime - last_retry_time > 10*60*1000 "
            + "ORDER BY id DESC LIMIT :limit")
    List<SysRecording> queryLimitRecordingByStatus2(List<Integer> uploadStatus, int retryCount,
                                                    long currentTime, int limit);

    @Update
    void updateRecording(SysRecording recording);

    @Update
    void updateStatusList(List<SysRecording> list);

    @Query("DELETE FROM sys_recording WHERE file_generate_time < :timestamp AND upload_status IN (:status)")
    void deleteOldRecording(long timestamp, List<Integer> status);

    @Query("UPDATE sys_recording SET upload_status= :newStatus WHERE call_id = :callId AND upload_status = :oldStatus")
    void updateStatusByKey(Long callId, int newStatus, int oldStatus);

}
