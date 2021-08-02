package com.yamsy.medreminder.ui.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yamsy.medreminder.SingletonHolder
import com.yamsy.medreminder.ui.data.room.MedRemindDatabase.Companion.DATABASE_NAME
import com.yamsy.medreminder.ui.data.room.MedRemindDatabase.Companion.TAG
import com.yamsy.medreminder.ui.data.room.MedRemindDatabase.Companion.VERSION
import com.yamsy.medreminder.ui.data.room.dao.MedTaskListDao
import com.yamsy.medreminder.ui.data.room.entity.MedTaskEntity
import com.yamsy.medreminder.util.Constants

@Database(entities = [MedTaskEntity::class]
        , version = VERSION)
abstract class MedRemindDatabase: RoomDatabase() {

    abstract fun medTaskListDao(): MedTaskListDao

    companion object: SingletonHolder<MedRemindDatabase, Context>(
        { context ->
            Log.i(TAG, "getDatabase new instance")
            Room.databaseBuilder(
                context,
                MedRemindDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    ) {
        const val TAG = "${Constants.LOG_PREFIX}RoomDb"
        const val VERSION = 1
        const val DATABASE_NAME = "med_tasks_db.db"
    }
}