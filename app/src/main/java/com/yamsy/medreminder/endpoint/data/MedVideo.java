package com.yamsy.medreminder.endpoint.data;

import com.google.gson.annotations.SerializedName;

public class MedVideo {

    @SerializedName("title")
    String mTitle;

    @SerializedName("hlsUrl")
    String mHlsUrl = "";

    @SerializedName("thumbnail")
    String mThumbNail = "";

    public MedVideo() {

    }

    public MedVideo(String title, String hlsUrl, String thumbnail) {
        mTitle = title;
        mHlsUrl = hlsUrl;
        mThumbNail = thumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mHlsUrl;
    }

    public String getThumbNail() {
        return mThumbNail;
    }

}
