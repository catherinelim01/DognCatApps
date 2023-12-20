package com.example.catdogapp;

import com.google.gson.annotations.SerializedName;

public class CatFact {
    @SerializedName("text")
    private String text;

    public String getFact() {return text;}
}
