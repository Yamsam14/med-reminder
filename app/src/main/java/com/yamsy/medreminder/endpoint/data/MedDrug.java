package com.yamsy.medreminder.endpoint.data;

import com.google.gson.annotations.SerializedName;

public class MedDrug {

    @SerializedName("brandNm")
    String mBrandName;

    @SerializedName("genericNm")
    String mGenericName;

    @SerializedName("qty")
    int mQuantity;

    @SerializedName("dosage")
    MedDosage mDosage;

    public MedDrug() {

    }

    public MedDrug(String brandName, String genericName, int qty, int dose, String unit) {
        mBrandName = brandName;
        mGenericName = genericName;
        mQuantity = qty;
        mDosage = new MedDosage(dose, unit);
    }

    public String getBrandName() {
        return mBrandName;
    }

    public String getGenericName() {
        return mGenericName;
    }

    public String getDoseStr() {
        return Integer.toString(mDosage.mDose * mQuantity) + " " + mDosage.mDoseUnit;
    }
}
