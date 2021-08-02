package com.yamsy.medreminder.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yamsy.medreminder.R
import com.yamsy.medreminder.databinding.LayoutMedicineTaskListItemBinding
import com.yamsy.medreminder.ui.data.MedTaskModel
import com.yamsy.medreminder.util.Constants
import com.yamsy.medreminder.util.GlideUtil

class MedicineListAdapter(private val mActionClickListener: View.OnClickListener)
            :RecyclerView.Adapter<MedicineListAdapter.MedTaskHolder>() {

    private val mTaskList = mutableListOf<MedTaskModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedTaskHolder {
        return MedTaskHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_medicine_task_list_item,
                parent,
                false
            ), mActionClickListener)
    }

    override fun onBindViewHolder(holder: MedTaskHolder, position: Int) {
        holder.bind(mTaskList[position])
    }

    override fun getItemCount(): Int {
        return mTaskList.size
    }

    fun setMedTaskList(newList: List<MedTaskModel>) {
        mTaskList.clear()
        mTaskList.addAll(newList)

        notifyDataSetChanged()
    }

    class MedTaskHolder(private val mBinding:LayoutMedicineTaskListItemBinding,
                        private val mActionClickListener: View.OnClickListener)
        :RecyclerView.ViewHolder(mBinding.root) {

            fun bind(medModel: MedTaskModel) {
                mBinding.apply {
                    val context = root.context

                    tvMedTaskAction.setTag(R.id.med_task_model, medModel)
                    tvVodTaskAction.setTag(R.id.med_task_model, medModel)

                    when (medModel.mMedTaskType) {
                        Constants.MedTaskType.MEDICINE -> {
                            clVodTask.visibility = View.GONE
                            clMedTask.visibility = View.VISIBLE
                            ivMedTaskType.setImageDrawable(context.resources.getDrawable(R.drawable.supplements, null))
                            tvMedName.text = medModel.mMedName
                            tvMedDose.text = medModel.mMedDose

                            when (medModel.mIsTaskDone) {
                                true -> {
                                    ivMedTaskDone.visibility = View.VISIBLE
                                    clMedContext.visibility = View.GONE
                                    clMedTask.background = null
                                }

                                else -> { //false
                                    ivMedTaskDone.visibility = View.INVISIBLE
                                    clMedContext.visibility = View.VISIBLE
                                    clMedTask.background = context.resources.getDrawable(R.drawable.rounder_corner, null)
                                    tvMedMealContext.text = medModel.mMealContext
                                    tvMedTaskAction.setOnClickListener(mActionClickListener)
                                }
                            }
                        }

                        else -> { //Vodtype
                            clVodTask.visibility = View.VISIBLE
                            clMedTask.visibility = View.GONE
                            ivMedTaskType.setImageDrawable(context.resources.getDrawable(R.drawable.vod, null))

                            when (medModel.mIsTaskDone) {
                                true -> {
                                    ivMedTaskDone.visibility = View.VISIBLE
                                    tvVodName.visibility = View.VISIBLE
                                    tvVodSubText.visibility = View.VISIBLE

                                    clVodTaskUnfinished.visibility = View.GONE

                                    clVodTask.background = null
                                    tvVodName.text = medModel.mVideoTitle
                                }

                                else -> { //false
                                    ivMedTaskDone.visibility = View.INVISIBLE
                                    tvVodName.visibility = View.GONE
                                    tvVodSubText.visibility = View.GONE

                                    clVodTaskUnfinished.visibility = View.VISIBLE

                                    clVodTask.background = context.resources.getDrawable(R.drawable.rounder_corner, null)
                                    tvVodTaskAction.setOnClickListener(mActionClickListener)
                                    tvVodTitle.text = medModel.mVideoTitle
                                    GlideUtil.loadThumbNail(ivVideoThumbnail, Uri.parse(medModel.mVideoThumbNail))
                                }
                            }
                        }
                    }
                }
            }
        }

}