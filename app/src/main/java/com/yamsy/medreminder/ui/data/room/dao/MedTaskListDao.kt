package com.yamsy.medreminder.ui.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yamsy.medreminder.ui.data.room.entity.MedTaskEntity

@Dao
interface MedTaskListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedTask(task: MedTaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedTasks(tasks: List<MedTaskEntity>)

    @Query("UPDATE med_task_list SET is_task_done = :status WHERE task_id = :taskId")
    suspend fun updateMedTaskStatus(taskId: String, status: Int)

    @Query("SELECT * FROM med_task_list")
    fun getAllMedTasks(): LiveData<List<MedTaskEntity>>
}