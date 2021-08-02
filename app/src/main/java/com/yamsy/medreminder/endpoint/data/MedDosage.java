package com.yamsy.medreminder.endpoint.data;

import com.google.gson.annotations.SerializedName;

public class MedDosage {

    @SerializedName("dose")
    int mDose;

    @SerializedName("unit")
    String mDoseUnit;

    MedDosage() {

    }

    MedDosage(int dose, String unit) {
        mDose = dose;
        mDoseUnit = unit;
    }


    public int getDose() {
        return mDose;
    }

    public String getDoseUnit() {
        return mDoseUnit;
    }
}
