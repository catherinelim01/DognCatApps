package com.example.catdogapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;

public class readJson {
    public static JSONObject readJsonFile(Context context) {
        try {
            // Get the AssetManager
            AssetManager assetManager = context.getAssets();

            // Open the JSON file
            InputStream inputStream = assetManager.open("Trivia.json");

            // Read the contents of the JSON file
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Convert the bytes to a string
            String jsonString = new String(buffer, "UTF-8");

            // Create a JSONObject from the string
            return new JSONObject(jsonString);

        } catch (Exception e) {
            Log.e("JsonFileReader", "Error reading JSON file", e);
        }

        return null;
    }
}
