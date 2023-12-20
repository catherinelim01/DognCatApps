package com.example.catdogapp;

import static com.example.catdogapp.readJson.readJsonFile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class page3Activity extends AppCompatActivity {
    private TextView questions;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    private int currentQuestionIndex = 0;

    private String answerKey;

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        Button next = (Button) findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);

        questions = findViewById(R.id.textView3);
        btnAnswer1 = findViewById(R.id.BtnAnswer1);
        btnAnswer2 = findViewById(R.id.BtnAnswer2);
        btnAnswer3 = findViewById(R.id.BtnAnswer3);
        btnAnswer4 = findViewById(R.id.BtnAnswer4);

        Button BtnGenerator = (Button) findViewById(R.id.BtnGenerator);
        Button BtnHome2 = (Button) findViewById(R.id.BtnHome2);

        BtnGenerator.setOnClickListener(v -> {
            Intent intent = new Intent(page3Activity.this, page2Activity.class);
            startActivity(intent);
        });

        BtnHome2.setOnClickListener(v -> {
            Intent intent = new Intent(page3Activity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView catImage = (ImageView) findViewById(R.id.triviaImage);

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

        nextButton();

        next.setOnClickListener(v -> {
            next.setVisibility(View.INVISIBLE);
            enableButton();
            nextButton();
        });

        btnAnswer1.setOnClickListener(v -> {
            if (btnAnswer1.getText().toString().equals(answerKey)) {
                btnAnswer1.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                Toast.makeText(getApplicationContext(), "Congratulations! You did it", Toast.LENGTH_SHORT).show();
            }
            else{
                btnAnswer1.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                Toast.makeText(getApplicationContext(), "Oh no! Try again, the answer is " + answerKey.toString(), Toast.LENGTH_SHORT).show();
            }
            unableButton();
            next.setVisibility(View.VISIBLE);
        });

        btnAnswer2.setOnClickListener(v -> {
            if (btnAnswer2.getText().toString().equals(answerKey)) {
                btnAnswer2.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                Toast.makeText(getApplicationContext(), "Congratulations! You did it", Toast.LENGTH_SHORT).show();
            }
            else{
                btnAnswer2.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                Toast.makeText(getApplicationContext(), "Oh no! Try again, the answer is " + answerKey.toString(), Toast.LENGTH_SHORT).show();
            }
            unableButton();
            next.setVisibility(View.VISIBLE);
        });

        btnAnswer3.setOnClickListener(v -> {
            if (btnAnswer3.getText().toString().equals(answerKey)) {
                btnAnswer3.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                Toast.makeText(getApplicationContext(), "Congratulations! You did it", Toast.LENGTH_SHORT).show();
            }
            else{
                btnAnswer3.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                Toast.makeText(getApplicationContext(), "Oh no! Try again, the answer is " + answerKey.toString(), Toast.LENGTH_SHORT).show();
            }
            unableButton();
            next.setVisibility(View.VISIBLE);
        });

        btnAnswer4.setOnClickListener(v -> {
            if (btnAnswer4.getText().toString().equals(answerKey)) {
                btnAnswer4.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                Toast.makeText(getApplicationContext(), "Congratulations! You did it", Toast.LENGTH_SHORT).show();
            }
            else{
                btnAnswer4.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                Toast.makeText(getApplicationContext(), "Oh no! Try again, the answer is " + answerKey.toString(), Toast.LENGTH_SHORT).show();
            }
            unableButton();
            next.setVisibility(View.VISIBLE);
        });

    }

    public void unableButton() {
        btnAnswer1.setEnabled(false);
        btnAnswer2.setEnabled(false);
        btnAnswer3.setEnabled(false);
        btnAnswer4.setEnabled(false);
    }

    public void enableButton() {
        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnAnswer4.setEnabled(true);
    }

    public void nextButton() {
        btnAnswer1.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
        btnAnswer2.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
        btnAnswer3.setBackgroundTintList(getResources().getColorStateList(R.color.orange));
        btnAnswer4.setBackgroundTintList(getResources().getColorStateList(R.color.orange));

        JSONObject obj = readJson.readJsonFile(this);
        try {
            JSONArray Array = obj.getJSONArray("cat");

            Random rand = new Random();

            int theNumber = rand.nextInt(Array.length());
            JSONObject text = Array.getJSONObject(theNumber);
            String question = text.getString("q");
            String response1 = text.getString("a1");
            String response2 = text.getString("a2");
            String response3 = text.getString("a3");
            String response4 = text.getString("a4");
            answerKey = text.getString("key");

            questions.setText(question);
            btnAnswer1.setText(response1);
            btnAnswer2.setText(response2);
            btnAnswer3.setText(response3);
            btnAnswer4.setText(response4);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}