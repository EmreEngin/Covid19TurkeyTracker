package com.engin.covid19.DownloaData;

public class ApiUtil {

    public static final String BASE_URL = "https://covid19.saglik.gov.tr";

    public static CovidInterface getCovidInterface(){
    return RetrofitClient.getClient(BASE_URL).create(CovidInterface.class);
    }
}
