package com.example.coronatracker.apiCall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetMethod {

    private GetMethod(){

    }

    private static final  String BASE_URL = "https://corona.lmao.ninja/v2/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
