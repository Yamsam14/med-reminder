package com.yamsy.medreminder.endpoint.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedTask {

    @SerializedName("scheduleCd")
    String mTaskId;

    @SerializedName("type")
    String mTaskType;

    @SerializedName("frequency")
    int mFrequency;

    @SerializedName("duration")
    int mDuration;

    @SerializedName("drug")
    MedDrug mDrugInfo = null;

    @SerializedName("sessionList")
    List<MedSession> mSessionList;

    @SerializedName("video")
    MedVideo mVideoInfo = null;

    MedTask() {

    }

    public MedTask(String taskId, String taskType, int freq, int dur,
                   MedDrug drugInfo, List<MedSession> sessionList, MedVideo videoInfo) {
        mTaskId = taskId;
        mTaskType = taskType;
        mFrequency = freq;
        mDuration = dur;
        mDrugInfo = drugInfo;
        mSessionList = sessionList;
        mVideoInfo = videoInfo;
    }

    public String getTaskId() {
        return mTaskId;
    }

    public String getTaskType() {
        return mTaskType;
    }

    public int getFrequency() {
        return mFrequency;
    }

    public int getDuration() {
        return mDuration;
    }

    public MedDrug getDrugInfo() {
        return mDrugInfo;
    }

    public List<MedSession> getSessionList() {
        return mSessionList;
    }

    public MedVideo getVideoInfo() {
        return mVideoInfo;
    }
}
