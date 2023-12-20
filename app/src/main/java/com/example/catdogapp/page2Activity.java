package com.example.catdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import retrofit2.Callback;





public class page2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        Button BtnTrivia = (Button) findViewById(R.id.BtnTrivia);
        Button BtnHome = (Button) findViewById(R.id.BtnHome);

        BtnTrivia.setOnClickListener(v -> {
            Intent intent = new Intent(page2Activity.this, page3Activity.class);
            startActivity(intent);
        });

        BtnHome.setOnClickListener(v -> {
            Intent intent = new Intent(page2Activity.this, MainActivity.class);
            startActivity(intent);
        });

        // image api
        ImageView catImage = (ImageView) findViewById(R.id.imageView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICatImages catApiService = retrofit.create(APICatImages.class);
        Call<List<CatImage>> call = catApiService.getCatImages();
        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.isSuccessful()) {
                    List<CatImage> catImages = response.body();
                    String imageUrl = catImages.get(0).getImageUrl();
                    Picasso.get().load(imageUrl).into(catImage);
                }
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                System.out.println("Failed");
                t.printStackTrace();
            }
        });

        nextFact();

        Button nextFact = (Button) findViewById(R.id.nextFact);

        nextFact.setOnClickListener(v -> {
            nextFact();
        });

    }
    public void nextFact(){
        TextView fact = (TextView) findViewById(R.id.fact);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://cat-fact.herokuapp.com/facts/random";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JsonParser jsonParser = new JsonParser();
                        Object obj = jsonParser.parse(response);
                        if (obj instanceof JsonObject) {
                            JsonObject jsonObj = (JsonObject) obj;

                            // Check if the fact contains only English words and no numbers
                            String factText = jsonObj.get("text").getAsString();
                            if (containsOnlyWords(factText)) {
                                fact.setText(factText);
                            } else {
                                nextFact();
                            }
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fact.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    private boolean containsOnlyWords(String text) {
        return text.matches("[a-zA-Z\\s.\"']+");
    }


}