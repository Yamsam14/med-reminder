package com.yamsy.medreminder.ui.data

import android.content.Context
import android.util.Log
import com.yamsy.medreminder.SingletonHolder
import com.yamsy.medreminder.endpoint.data.MedTask
import com.yamsy.medreminder.ui.data.room.MedRemindDatabase
import com.yamsy.medreminder.ui.data.room.SharedPreferenceHelper
import com.yamsy.medreminder.ui.data.room.entity.MedTaskEntity
import com.yamsy.medreminder.util.Constants
import java.text.SimpleDateFormat
import java.util.*

class LocalDataSource private constructor(context: Context) {

    private val mPreferenceHelper = SharedPreferenceHelper.getInstance(context)
    private val mDatabase = MedRemindDatabase.getInstance(context)

    private val mMedTaskListDao = mDatabase.medTaskListDao()

    suspend fun addTasks(taskList: List<MedTask>?) {
        taskList ?: return

        val time = mPreferenceHelper.getValue(SharedPreferenceHelper.PRESCRIPTION_TIME, Constants.DEFAULT_PRESCRIPTION_TIME)

        if (time == Constants.DEFAULT_PRESCRIPTION_TIME) {
            return
        }

        val dateFormat = SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT)
        val calendar = Calendar.getInstance()

        val taskEntityList = mutableListOf<MedTaskEntity>()

        for (task in taskList) {
            calendar.timeInMillis = time //reset back to Prescription time

            var id: String
            with (task) {
                val medTaskType = if (taskType.equals(Constants.MedTaskType.VOD.title, true)) {
                    Constants.MedTaskType.VOD.ordinal
                } else {
                    Constants.MedTaskType.MEDICINE.ordinal
                }

                for (i in 0 until duration) { // no of days the task has to be re-created
                    calendar.add(Calendar.DATE, i * frequency)

                    for (session in sessionList) {
                        id = dateFormat.format(calendar.time)

                        val sessionType = if (session.session.equals(Constants.MedSessionType.MORNING.title, true)) {
                            Constants.MedSessionType.MORNING.ordinal
                        } else if (session.session.equals(Constants.MedSessionType.NOON.title, true)) {
                            Constants.MedSessionType.NOON.ordinal
                        } else if (session.session.equals(Constants.MedSessionType.EVENING.title, true)) {
                            Constants.MedSessionType.EVENING.ordinal
                        } else {
                            Constants.MedSessionType.NIGHT.ordinal
                        }

                        id = id + "_" + sessionType + "_" + taskId

                        val taskEntity = MedTaskEntity.createEntity(
                            id, taskId, medTaskType, sessionType, session.foodContext,
                            drugInfo?.brandName, drugInfo?.genericName, drugInfo?.doseStr,
                            videoInfo?.title, videoInfo?.url, videoInfo?.thumbNail, false)
                        //Log.d(TAG, "TaskAdded.. ${taskEntity.taskId}")
                        taskEntityList.add(taskEntity)
                    } //for (session in sessionList)

                    calendar.timeInMillis = time
                } // for (0 until duration)
            } //with (task)
        } //for (task in taskList)

        mMedTaskListDao.insertMedTasks(taskEntityList)
    }

    suspend fun updateTaskStatus(taskId: String, status: Int) {
        mMedTaskListDao.updateMedTaskStatus(taskId, status)
    }

    fun getAllMedTasksLive() = mMedTaskListDao.getAllMedTasks()

    companion object: SingletonHolder<LocalDataSource, Context>(::LocalDataSource) {
        private val TAG = Constants.LOG_PREFIX + "LocalDataSource"
    }
}