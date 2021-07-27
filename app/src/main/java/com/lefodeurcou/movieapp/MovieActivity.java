package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {

    private TextView extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        this.extras = findViewById(R.id.extras);
        this.extras.setText(extras.getCharSequence("id_movie"));
    }
}