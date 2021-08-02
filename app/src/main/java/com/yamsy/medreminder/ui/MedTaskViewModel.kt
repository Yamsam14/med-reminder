package com.yamsy.medreminder.ui

import androidx.lifecycle.*
import com.yamsy.medreminder.ui.data.LocalDataSource
import com.yamsy.medreminder.ui.data.MedTaskModel
import com.yamsy.medreminder.ui.data.room.entity.MedTaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class MedTaskViewModel(private val mLocalDataSource: LocalDataSource): ViewModel() {

    private val medTasks = mLocalDataSource.getAllMedTasksLive()

    private val medTaskList = MediatorLiveData<List<MedTaskModel>>()

    init {
        medTaskList.addSource(medTasks) {
            medTaskList.value = medTaskEntityListToModel(it)
        }
    }

    fun getMedRemindTaskList(): LiveData<List<MedTaskModel>> = medTaskList

    private fun medTaskEntityListToModel(entityList: List<MedTaskEntity>): List<MedTaskModel> {
        val models = mutableListOf<MedTaskModel>()
        for (entity in entityList) {
            models.add(entity.toMedTaskModel())
        }
        return models
    }

    fun updateMedTaskStatus(taskId: String, status: Boolean) {
        val updateStatus = when (status) {
            true -> 1
            else -> 0
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mLocalDataSource.updateTaskStatus(taskId, updateStatus)
            }
        }
    }
}


class MedTaskViewModelFactory(private val dataSource: LocalDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MedTaskViewModel::class.java)) {
            MedTaskViewModel(dataSource) as T
        } else {
            throw IllegalArgumentException("ViewModel not found $modelClass")
        }
    }
}