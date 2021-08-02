package com.yamsy.medreminder.ui.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yamsy.medreminder.ui.data.MedTaskModel
import com.yamsy.medreminder.util.Constants

@Entity(tableName = "med_task_list")
data class MedTaskEntity(
    @ColumnInfo(name = "task_id")
    @PrimaryKey
    val taskId: String,

    @ColumnInfo(name = "task_name")
    val taskName: String,

    @ColumnInfo(name = "task_type")
    val taskType: Int,

    @ColumnInfo(name = "session")
    val session: Int,

    @ColumnInfo(name = "food_context")
    val foodContext: String,

    @ColumnInfo(name = "drug_name")
    val drugName: String?,

    @ColumnInfo(name = "drug_gen_name")
    val drugGenericName: String?,

    @ColumnInfo(name = "dosage")
    val dosage: String?,

    @ColumnInfo(name = "v_title")
    val vodTitle: String?,

    @ColumnInfo(name = "v_url")
    val vodHlsUrl: String?,

    @ColumnInfo(name = "v_thumbnail")
    val vodThumbNail: String?,

    @ColumnInfo(name = "is_task_done")
    val isTaskDone: Boolean
) {
    fun toMedTaskModel() = MedTaskModel(
        taskId,
        Constants.MedTaskType.values()[taskType],
        Constants.MedSessionType.values()[session],
        foodContext,
        drugName,
        dosage,
        vodTitle,
        vodHlsUrl,
        vodThumbNail,
        isTaskDone
    )

    companion object {
        fun createEntity(taskId: String, taskName: String, taskType: Int, sessionType: Int, foodContext: String,
                         drugName: String?, drugGenericName: String?, doseStr: String?,
                         vodTitle: String?, vodHlsUrl: String?, vodThumbNail: String?,
                         isTaskDone: Boolean) = MedTaskEntity(
            taskId, taskName, taskType, sessionType, foodContext,
            drugName, drugGenericName, doseStr,
            vodTitle, vodHlsUrl, vodThumbNail, isTaskDone)
    }
}
