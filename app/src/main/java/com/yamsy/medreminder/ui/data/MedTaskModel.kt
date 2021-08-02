package com.yamsy.medreminder.ui.data

import com.yamsy.medreminder.util.Constants

data class MedTaskModel(val mMedTaskId: String,
                        val mMedTaskType: Constants.MedTaskType,
                        val mMedSession: Constants.MedSessionType,
                        val mMealContext: String,
                        val mMedName: String? = null,
                        val mMedDose: String? = null,
                        val mVideoTitle: String? = null,
                        val mVideoUrl: String? = null,
                        val mVideoThumbNail: String? = null,
                        val mIsTaskDone: Boolean = false
)
