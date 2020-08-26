package com.example.coronatracker.apiInterface;

import com.example.coronatracker.jsonClass.AllCorornaResult;
import com.example.coronatracker.jsonClass.CoronaApiCurl;
import com.example.coronatracker.jsonClass.SpecificCountryCoronaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("all")
    Call<AllCorornaResult> getAllResult();

    @GET("countries/{country}")
    Call<SpecificCountryCoronaAPI> getSpecificCountryCase(
            @Path("country") String country
    );

    @GET("countries")
    Call<List<CoronaApiCurl>> getCountryCases(
            @Query("sort") String country
    );
}
