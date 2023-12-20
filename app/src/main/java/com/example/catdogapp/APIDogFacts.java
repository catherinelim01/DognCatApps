package com.example.catdogapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIDogFacts {
    @GET("/api/v2/facts")
    Call<List<CatFact>> getCatFacts();
}
