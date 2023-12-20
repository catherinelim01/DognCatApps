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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import retrofit2.Callback;

public class page2dog extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        Button BtnTrivia = (Button) findViewById(R.id.BtnTrivia);
        Button BtnHome = (Button) findViewById(R.id.BtnHome);

        BtnTrivia.setOnClickListener(v -> {
            Intent intent = new Intent(page2dog.this, page3dog.class);
            startActivity(intent);
        });

        BtnHome.setOnClickListener(v -> {
            Intent intent = new Intent(page2dog.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView dogImage = (ImageView) findViewById(R.id.imageView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIDogImages catApiService = retrofit.create(APIDogImages.class);
        Call<List<DogImage>> call = catApiService.getDogImages();
        call.enqueue(new Callback<List<DogImage>>() {
            @Override
            public void onResponse(Call<List<DogImage>> call, Response<List<DogImage>> response) {
                if (response.isSuccessful()) {
                    List<DogImage> dogImages = response.body();
                    String imageUrl = dogImages.get(0).getImageUrl();
                    Picasso.get().load(imageUrl).into(dogImage);
                }
            }

            @Override
            public void onFailure(Call<List<DogImage>> call, Throwable t) {
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
        String url = "https://dogapi.dog/api/v2/facts";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JsonParser jsonParser = new JsonParser();
                        Object obj = jsonParser.parse(response);
                        if (obj instanceof JsonObject) {
                            JsonObject jsonObj = (JsonObject) obj;
                            String dataString = String.valueOf(jsonObj.get("data"));
                            JsonParser jsonParserData = new JsonParser();
                            Object objData = jsonParserData.parse(dataString);
                            String Data;
                            try {
                                JSONArray array = new JSONArray(dataString);
                                String datass = array.get(0).toString();
                                JSONObject object = new JSONObject(datass);
                                Data = object.get("attributes").toString();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JsonParser jsonParserAtt = new JsonParser();
                            Object objAtt = jsonParserAtt.parse(Data);
                            JsonObject jsonObjData = (JsonObject) objAtt;
                            Data = jsonObjData.get("body").getAsString();
                            String factText = String.valueOf(Data);
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
