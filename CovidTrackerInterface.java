package com.example.publicohelpline.covidtracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidTrackerInterface {
    static final String Base_URL = "https://corona.lmao.ninja/v2/";
    @GET("countries")
    Call<List<CountryData>> getCountryData();

}
