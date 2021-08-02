package com.yamsy.medreminder.endpoint.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrescriptionData {

    @SerializedName("scheduleList")
    List<MedTask> mTaskScheduleList;

    PrescriptionData() {

    }

    public List<MedTask> getTaskList() {
        return mTaskScheduleList;
    }
}
