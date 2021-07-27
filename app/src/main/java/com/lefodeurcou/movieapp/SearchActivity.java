package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.home = findViewById(R.id.home);
        this.home.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   finish();
               }
           }
        );
    }
}