package com.yamsy.medreminder.endpoint;

import com.yamsy.medreminder.endpoint.data.PrescriptionData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    String BASE_URL = "https://38rhabtq01.execute-api.ap-south-1.amazonaws.com/";

    @GET("dev/remind/schedule")
    Call<PrescriptionData> getPrescriptionData();

}
