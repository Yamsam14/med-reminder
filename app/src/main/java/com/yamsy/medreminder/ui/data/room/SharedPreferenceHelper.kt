package com.yamsy.medreminder.ui.data.room

import android.content.Context
import com.yamsy.medreminder.SingletonHolder
import java.lang.Exception

class SharedPreferenceHelper private constructor(private val mContext: Context) {

    fun put(key: String?, value: Long) {
        val pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getValue(key: String?, defValue: Long) : Long {
        val pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return try {
            pref.getLong(key, defValue)
        } catch (e: Exception) {
            defValue
        }
    }

    companion object: SingletonHolder<SharedPreferenceHelper, Context>(:: SharedPreferenceHelper) {
        private const val PREF_NAME = "med_remind_shared_prefs"

        const val PRESCRIPTION_TIME = "prescription_time" //Long
    }
}