package com.yamsy.medreminder.endpoint.data;

import com.google.gson.annotations.SerializedName;

public class MedSession {

    @SerializedName("session")
    String mSession = "";

    @SerializedName("foodContext")
    String mFoodContext = "";

    public MedSession() {

    }

    public MedSession(String session, String foodContext) {
        mSession = session;
        mFoodContext = foodContext;
    }

    public String getSession() {
        return mSession;
    }

    public String getFoodContext() {
        return mFoodContext;
    }

}
