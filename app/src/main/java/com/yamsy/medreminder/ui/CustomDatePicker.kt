package com.yamsy.medreminder.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.yamsy.medreminder.R
import com.yamsy.medreminder.databinding.LayoutDatePickerBinding
import com.yamsy.medreminder.eventbus.DateChangeEvent
import com.yamsy.medreminder.eventbus.MedReminderEventBus
import java.util.*

class CustomDatePicker: FrameLayout, View.OnClickListener {

    private lateinit var mDatePickerLayout: LayoutDatePickerBinding
    private val mCalendar = Calendar.getInstance()

    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.cl_arrow_decrement -> {
                mCalendar.add(Calendar.DATE, -1)
                MedReminderEventBus.publish(DateChangeEvent(mCalendar.timeInMillis))
                updateDateUI()
            }

            R.id.cl_arrow_increment -> {
                mCalendar.add(Calendar.DATE, 1)
                MedReminderEventBus.publish(DateChangeEvent(mCalendar.timeInMillis))
                updateDateUI()
            }
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mDatePickerLayout = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_date_picker, this, true)

        mDatePickerLayout.run {
            clArrowDecrement.setOnClickListener(this@CustomDatePicker)
            clArrowIncrement.setOnClickListener(this@CustomDatePicker)
        }

        updateDateUI()
    }

    private fun updateDateUI() {
        mDatePickerLayout.run {
            tvDate.text = mCalendar.get(Calendar.DATE).toString()
            tvMonth.text = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        }
    }

}