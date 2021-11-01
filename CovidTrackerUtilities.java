package com.example.publicohelpline.covidtracker;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidTrackerUtilities {
    private static Retrofit retrofit = null;
    public static CovidTrackerInterface getcovidTrackerInterface(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(CovidTrackerInterface.Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(CovidTrackerInterface.class);


    }
}
