package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView welcome;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Blah blah", Toast.LENGTH_SHORT).show();
        this.welcome = findViewById(R.id.welcome);
        this.welcome.setText(R.string.welcome);

        this.search = findViewById(R.id.search);
        this.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent myIntent = new Intent(v.getContext(), SearchActivity.class);
                    startActivity(myIntent);
                }
            }
        );
    }

    public void onClickFilms(View v)
    {
        Toast.makeText(this, "Film clicked", Toast.LENGTH_SHORT).show();
    }
}