package com.example.catdogapp;

import com.google.gson.annotations.SerializedName;

public class DogFact {
    @SerializedName("body")
    private String body;

    public String getFact() {return body;}
}
