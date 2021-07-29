package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView welcome;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Blah blah", Toast.LENGTH_SHORT).show();
        this.welcome = findViewById(R.id.welcome);
        this.welcome.setText(R.string.welcome);

        this.search = findViewById(R.id.search);
        this.search.setOnClickListener(v -> {
                Intent myIntent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(myIntent);
            }
        );
    }

    public void onClickMovies(View v)
    {
        Intent myIntent = new Intent(v.getContext(), MovieActivity.class);
        myIntent.putExtra("id_movie", getResources().getResourceName(v.getId()));
        startActivity(myIntent);
    }
}