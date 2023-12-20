package com.example.catdogapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICatFacts {
    @GET("/facts/random")
    Call<List<CatFact>> getCatFacts();
}
