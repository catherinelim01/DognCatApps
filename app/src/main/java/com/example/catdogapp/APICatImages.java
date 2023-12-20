package com.example.catdogapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICatImages {
    @GET("/V1/images/search")
    Call<List<CatImage>> getCatImages();
}
