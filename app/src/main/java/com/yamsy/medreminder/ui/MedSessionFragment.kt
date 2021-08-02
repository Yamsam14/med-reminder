package com.yamsy.medreminder.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yamsy.medreminder.R
import com.yamsy.medreminder.eventbus.DateChangeEvent
import com.yamsy.medreminder.eventbus.MedReminderEventBus
import com.yamsy.medreminder.ui.data.LocalDataSource
import com.yamsy.medreminder.ui.data.MedTaskModel
import com.yamsy.medreminder.util.Constants
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_medicine_list_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class MedSessionFragment: Fragment {

    private lateinit var mViewModel: MedTaskViewModel
    private lateinit var mTaskListAdapter: MedicineListAdapter
    private val mEventDisposable = CompositeDisposable()
    private val mCalendar = Calendar.getInstance()
    private val mDateFormat = SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT)
    private var mSessionType: Int = 0

    private val mActionListener = View.OnClickListener {
        val taskModel = it.getTag(R.id.med_task_model) as? MedTaskModel

        taskModel ?: return@OnClickListener

        mViewModel.updateMedTaskStatus(taskModel.mMedTaskId, true)
    }

    constructor(): super() {
    }

    constructor(sessionType: Int): super() {
        mSessionType = sessionType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        mSessionType = savedInstanceState?.getInt(CURRENT_SELECTED_TAB_POSITION) ?: mSessionType

        return inflater.inflate (R.layout.layout_medicine_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = getTaskIdPrefix()

        mViewModel = ViewModelProvider(this,
            MedTaskViewModelFactory(LocalDataSource.getInstance(requireContext()))
        ).get(MedTaskViewModel::class.java)

        mTaskListAdapter = MedicineListAdapter(mActionListener)
        rv_tasks.apply {
            adapter = mTaskListAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
        observeOnData(id)

        mEventDisposable.add(
            MedReminderEventBus.listen(DateChangeEvent::class.java).subscribe {
                mViewModel.getMedRemindTaskList().removeObservers(this)
                mCalendar.timeInMillis = it.timeInMillis
                observeOnData(getTaskIdPrefix())
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        mEventDisposable.dispose()
    }

    private fun getTaskIdPrefix(): String {
        return mDateFormat.format(mCalendar.time) + "_" + mSessionType
    }

    private fun observeOnData(id: String) {
        mViewModel.getMedRemindTaskList().observe(
            this,
                androidx.lifecycle.Observer {  allTasks ->
                    val list = allTasks.filter {
                        it.mMedTaskId.contains(id)
                    }

                    Log.d(TAG, "MedRemindTaskList updated.. ${allTasks.size} filtered ${list.size}")

                    progress_bar.visibility = View.GONE
                    if (list.isEmpty()) {
                        tv_empty_list.visibility = View.VISIBLE
                        rv_tasks.visibility = View.GONE
                    } else {
                        tv_empty_list.visibility = View.GONE
                        rv_tasks.visibility = View.VISIBLE
                        (rv_tasks.adapter as MedicineListAdapter).setMedTaskList(list)
                    }
                }
            )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_SELECTED_TAB_POSITION, mSessionType)
        Log.d(TAG, "onSaveInstanceState $mSessionType")
    }


    companion object {
        const val TAG = Constants.LOG_PREFIX + "Fragment"
        private const val CURRENT_SELECTED_TAB_POSITION = "SELECTED_TAB_POSITION"
    }
}

class MedSessionFragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return MedSessionFragment(position) // as position == Constants.MedSessionType.values()[position].ordinal()
    }

    override fun getItemCount(): Int {
        return Constants.MedSessionType.values().size
    }
}