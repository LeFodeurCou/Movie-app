package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lefodeurcou.movieapp.adapters.SearchAdapter;
import com.lefodeurcou.movieapp.models.Movie;
import com.lefodeurcou.movieapp.utils.ApiFetch;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView welcome;
    private Button search;
    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView wrapper;
    private SearchAdapter searchAdapter;

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
//        this.welcome = findViewById(R.id.welcome);
//        this.welcome.setText(R.string.welcome);

        this.search = findViewById(R.id.search);
        this.search.setOnClickListener(v -> {
                Intent myIntent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(myIntent);
            }
        );
        this.wrapper = findViewById(R.id.recycler);
        this.wrapper.setLayoutManager(new GridLayoutManager(this, 3));
        this.searchAdapter = new SearchAdapter(this, movies);
        this.wrapper.setAdapter(this.searchAdapter);
        this.fillMovies();
    }

    private void fillMovies()
    {
        this.movies.removeAll(this.movies);
        Log.d("lol", "Filling");
        SharedPreferences preferences = getSharedPreferences("bookmarks", Context.MODE_PRIVATE);
        Map<String, ?> bookmarks = preferences.getAll();
        Log.d("lol", bookmarks.toString());
        for (Map.Entry<String, ?> bookmark:
             bookmarks.entrySet()) {
            Log.d("lol", bookmark.getKey());
            this.loadMovies(bookmark.getKey());
        }
    }

    private void loadMovies(String imdb)
    {
        String url = "http://www.omdbapi.com/?apikey=bf4e1adb&i=".concat(imdb);
        Log.d("lol", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        Log.d("lol", response.toString());
                        if (response.getString("Response") == "False")
                            return;
                        Movie movie = new Movie();
                        movie.setTitle(response.getString("Title"));
                        movie.setDate(response.getString("Released"));;
                        movie.setDesc(response.getString("Plot"));;
                        movie.setGenre(response.getString("Genre"));;
                        movie.setReleased(response.getString("Released"));
                        movie.setDirector(response.getString("Director"));
                        movie.setActors(response.getString("Actors"));
                        movie.setAwards(response.getString("Awards"));
                        movie.setImgUrl(response.getString("Poster"));
                        movie.setIMDb(response.getString("imdbID"));
                        MainActivity.this.movies.add(movie);
                        this.searchAdapter.notifyDataSetChanged();
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
    }

    public void onClickMovies(View v)
    {
        Intent myIntent = new Intent(v.getContext(), MovieActivity.class);
        myIntent.putExtra("id_movie", getResources().getResourceName(v.getId()));
        startActivity(myIntent);
    }
}