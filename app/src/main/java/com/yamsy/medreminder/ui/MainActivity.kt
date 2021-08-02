package com.yamsy.medreminder.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yamsy.medreminder.R
import com.yamsy.medreminder.databinding.ActivityMainBinding
import com.yamsy.medreminder.endpoint.RetrofitClient
import com.yamsy.medreminder.endpoint.data.PrescriptionData
import com.yamsy.medreminder.ui.data.LocalDataSource
import com.yamsy.medreminder.ui.data.room.SharedPreferenceHelper
import com.yamsy.medreminder.util.Constants
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity: AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mLocalDataSource: LocalDataSource
    private lateinit var mFragmentAdapter: MedSessionFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        setContentView(mBinding.root)

        mLocalDataSource = LocalDataSource.getInstance(applicationContext)

        init()
        getTasks()
    }

    private fun init() {
        mFragmentAdapter = MedSessionFragmentAdapter(this)
        mBinding.timingsViewPager.adapter = mFragmentAdapter

        TabLayoutMediator(mBinding.timingsTabLayout, mBinding.timingsViewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = Constants.MedSessionType.values()[position].title
        }).attach()
    }

    private fun getTasks() {
        val time = SharedPreferenceHelper.getInstance(applicationContext).getValue(
            SharedPreferenceHelper.PRESCRIPTION_TIME, Constants.DEFAULT_PRESCRIPTION_TIME)
        if (time != Constants.DEFAULT_PRESCRIPTION_TIME) {
            Log.d(TAG, "Tasks are already available. Not Querying from server again!")
            return
        }

        val call: Call<PrescriptionData> = RetrofitClient.getInstance().apiService.prescriptionData
        call.enqueue(object: Callback<PrescriptionData> {

            override fun onResponse(
                call: Call<PrescriptionData>,
                response: Response<PrescriptionData>
            ) {
                var prescriptionData = response.body()
                Log.d(TAG, "onResponse prescriptionData: $prescriptionData")

                prescriptionData?.run {
                    SharedPreferenceHelper.getInstance(applicationContext).put(
                        SharedPreferenceHelper.PRESCRIPTION_TIME, Calendar.getInstance().timeInMillis)

                    launch {
                        withContext(Dispatchers.IO) {
                            mLocalDataSource.addTasks(prescriptionData.taskList)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PrescriptionData>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        const val TAG = Constants.LOG_PREFIX + "MainActivity"
    }
}