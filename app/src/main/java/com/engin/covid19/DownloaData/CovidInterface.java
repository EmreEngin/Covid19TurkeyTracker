package com.engin.covid19.DownloaData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidInterface {

    @GET("/covid19api?getir=liste")
    Call<List<Covidata>> veriAl();
}
