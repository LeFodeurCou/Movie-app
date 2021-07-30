package com.lefodeurcou.movieapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lefodeurcou.movieapp.models.Movie;
import com.lefodeurcou.movieapp.utils.ApiFetch;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class MovieActivity extends AppCompatActivity {

    private TextView extras;
    private Movie movie = new Movie();
    private Boolean isSeeMoreClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_movie);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected())
            return;

        Bundle extras = getIntent().getExtras();
        // IMDb for Star Wars
        String url = "http://www.omdbapi.com/?apikey=bf4e1adb&i=".concat(String.valueOf(extras.getCharSequence("imdb", "tt0076759")));
        Log.d("lol", url);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        Log.d("lol", response.toString());
                        if (response.getString("Response") == "False")
                            return;
                        MovieActivity.this.movie.setTitle(response.getString("Title"));
                        MovieActivity.this.movie.setDate(response.getString("Released"));;
                        MovieActivity.this.movie.setDesc(response.getString("Plot"));;
                        MovieActivity.this.movie.setGenre(response.getString("Genre"));;
                        MovieActivity.this.movie.setReleased(response.getString("Released"));
                        MovieActivity.this.movie.setDirector(response.getString("Director"));
                        MovieActivity.this.movie.setActors(response.getString("Actors"));
                        MovieActivity.this.movie.setAwards(response.getString("Awards"));
                        MovieActivity.this.movie.setImgUrl(response.getString("Poster"));
                        MovieActivity.this.movie.setIMDb(response.getString("imdbID"));
                        this.updateUi();
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("lol", "success");
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("lol", "failure");
                        // TODO: Handle error

                    }
                });
        ApiFetch.getInstance(this).addToRequestQueue(jsonObjectRequest);

        Button seeMore = findViewById(R.id.see_more);
        seeMore.setOnClickListener(v -> {
            TextView plot = findViewById(R.id.plot);
            if (!this.isSeeMoreClicked)
                plot.setMaxLines(Integer.MAX_VALUE);
            else
                plot.setMaxLines(3);
            this.isSeeMoreClicked = !this.isSeeMoreClicked;
        });
    }

    protected void updateUi()
    {
        TextView text;
        text = findViewById(R.id.title);
        text.setText(this.movie.getTitle());
        text = findViewById(R.id.date);
        text.setText(this.movie.getDate());
        text = findViewById(R.id.plot);
        text.setText(this.movie.getDesc());
        text = findViewById(R.id.director);
        text.setText("Director : ".concat(this.movie.getDirector()));
        text = findViewById(R.id.actors);
        text.setText("Actors : ".concat(this.movie.getActors()));
        text = findViewById(R.id.awards);
        text.setText("Awards : ".concat(this.movie.getAwards()));
        ImageView poster = findViewById(R.id.poster);
        poster.setLayoutParams(new LinearLayout
                .LayoutParams(
                200 * (int) getResources().getDisplayMetrics().density,
                200 * (int) getResources().getDisplayMetrics().density
        ));
        Picasso.get()
                .load(this.movie.getImgUrl())
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
                .into(poster);

        SharedPreferences preferences = getSharedPreferences("bookmarks", Context.MODE_PRIVATE);
        String bookmarks = preferences.getString(this.movie.getIMDb(), "");
        if (!bookmarks.equals("")) {
            ImageView img = findViewById(R.id.heart);
            img.setBackgroundResource(R.drawable.heart_red);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClickBookmark(View view)
    {
        SharedPreferences preferences = getSharedPreferences("bookmarks", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        String imdb = this.movie.getIMDb();

        String bookmarks = preferences.getString(imdb, "");
        if (bookmarks.equals("")) {
            edit.putString(imdb, imdb);
            ImageView img = findViewById(R.id.heart);
            img.setBackgroundResource(R.drawable.heart_red);
            Log.d("lol", "Put movie");
        }
        else {
            edit.remove(imdb);
            ImageView img = findViewById(R.id.heart);
            img.setBackgroundResource(R.drawable.heart);
            Log.d("lol", "Remove movie");
        }
        edit.commit();
        Log.d("lol", "Clicked");
        Log.d("lol", preferences.getString(imdb, "default"));
    }
}