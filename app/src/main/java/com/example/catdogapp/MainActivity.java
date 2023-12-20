package com.example.catdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton BtnCat = (ImageButton) findViewById(R.id.BtnCat);
        ImageButton BtnDog = (ImageButton) findViewById(R.id.BtnDog);

        BtnCat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, page2Activity.class);
            startActivity(intent);
        });

        BtnDog.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, page2dog.class);
            startActivity(intent);
        });
    }
}