package com.ipusoft.context.db.dao;


import androidx.databinding.ObservableList;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ipusoft.context.bean.CallTaskDetails;
import com.ipusoft.context.bean.CallTaskDetailsCount;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * author : GWFan
 * time   : 4/28/21 11:35 AM
 * desc   :
 */

@Dao
public interface CallTaskDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<CallTaskDetails> detailList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CallTaskDetails details);

    @Query("SELECT * FROM call_task_details WHERE deleted != 1 AND task_id = :taskId ORDER BY status asc")
    List<CallTaskDetails> queryById(long taskId);

    @Query("SELECT * FROM call_task_details WHERE deleted != 1 AND task_id = :taskId ORDER BY status asc")
    Observable<List<CallTaskDetails>> queryById1(long taskId);

    @Query("SELECT * FROM call_task_details WHERE deleted != 1 AND status = :status AND task_id IN (:taskIds) ORDER BY status asc")
    List<CallTaskDetails> queryByIds(List<Long> taskIds, Integer status);

    @Query("SELECT * FROM call_task_details WHERE deleted != 1 AND task_id IN (:taskIds) ORDER BY status asc")
    Observable<List<CallTaskDetails>> queryByIds(List<Long> taskIds);

    /**
     * select customer_id, count(customer_id) as "count" from cdr_call_item a where 1 = 1 and a.cid=4574421855764502 and clue = 0 and customer_id > 0
     * and begin_time >= '2023-04-20 00:00:00' and '2023-04-20 23:59:59' >= begin_time and
     * (a.user_id=4828126077255700 or a.department_id in (4574421880930311,4574857589424164)) group by customer_id
     *
     * @param taskIds
     * @param status
     * @return
     */
    @Query("SELECT task_id as taskId, count(task_id) as count FROM call_task_details "
            + "WHERE deleted != 1 AND task_id IN (:taskIds) AND status IN (:status) GROUP BY task_id")
    List<CallTaskDetailsCount> queryDetailsByIdsAndStatus(List<Long> taskIds, Integer... status);

    @Update
    void update(CallTaskDetails record);

    @Update
    void update(List<CallTaskDetails> record);

    @Query("DELETE FROM call_task_details WHERE id = :id")
    void deleteRecord(Long id);

    @Query("UPDATE call_task_details SET status=:status , callee_status = :calleeStatus, call_time = :currentTime WHERE task_id = :taskId AND callee = :phone")
    void update(long taskId, String phone, int calleeStatus, int status, String currentTime);
}
