package com.example.catdogapp;

import android.media.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIDogImages {
    @GET("/V1/images/search")
    Call<List<DogImage>> getDogImages();
}
