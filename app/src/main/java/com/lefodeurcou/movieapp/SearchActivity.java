package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lefodeurcou.movieapp.adapters.SearchAdapter;
import com.lefodeurcou.movieapp.models.Movie;
import com.lefodeurcou.movieapp.utils.ApiFetch;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private Button home;
    private TextView extras;
    private ScrollView movie_list;
    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView wrapper;
    private EditText searchField;
    private String url;
    private SearchAdapter searchAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.movie_list = findViewById(R.id.movie_list);
        this.wrapper = findViewById(R.id.recycler);
        this.wrapper.setLayoutManager(new GridLayoutManager(this, 3));
        this.searchAdapter = new SearchAdapter(this, movies);
        this.wrapper.setAdapter(this.searchAdapter);

        this.searchField = findViewById(R.id.search_field);
        this.searchField.setOnEditorActionListener((tv, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_DONE){
                SearchActivity.this.onClickGo(tv);
            }
            return false;
        });

        this.home = findViewById(R.id.home);
        this.home.setOnClickListener(v -> finish()
        );
    }

    private LinearLayout createMovieRow(Movie movie)
    {
        LinearLayout row = new LinearLayout(getApplicationContext());
        row.setOrientation(LinearLayout.HORIZONTAL);

        ImageView img = new ImageView(getApplicationContext());
        img.setLayoutParams(new LinearLayout
                .LayoutParams(
                200 * (int) getResources().getDisplayMetrics().density,
                200 * (int) getResources().getDisplayMetrics().density
        ));
        Picasso.get()
                .load(movie.getImgUrl())
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
                .into(img);
        row.addView(img);

        LinearLayout texts = new LinearLayout(getApplicationContext());
        texts.setOrientation(LinearLayout.VERTICAL);
        row.addView(texts);

        TextView movie_title = new TextView(getApplicationContext());
        movie_title.setText(movie.getTitle());
        texts.addView(movie_title);

        TextView movie_desc = new TextView(getApplicationContext());
        movie_desc.setText(movie.getDesc());
        texts.addView(movie_desc);


        return row;
    }

    public void onClickGo(View v)
    {
        this.page = 1;
        this.movies.removeAll(this.movies);
        this.wrapper.removeAllViews();

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.searchField.getWindowToken(), 0);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected())
            return;
        this.url = "http://www.omdbapi.com/?apikey=bf4e1adb&s=".concat(String.valueOf(this.searchField.getText()));
        loadPage();
    }

    public void loadPage()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, this.url + "&page=" + String.valueOf(this.page), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray respArray = null;
                        try {
                            if (response.getString("Response") == "False")
                                return;
                            respArray = response.getJSONArray("Search");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (respArray == null)
                            return;
                        int length = respArray.length();
                        for (int i = 0; i < length; i++)
                        {
                            Movie movie = new Movie();
                            try {
                                JSONObject jsonObject = respArray.getJSONObject(i);
                                movie.setIMDb(jsonObject.getString("imdbID"));
                                movie.setTitle(jsonObject.getString("Title"));
                                movie.setReleased(jsonObject.getString("Year"));
                                movie.setImgUrl(jsonObject.getString("Poster"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SearchActivity.this.movies.add(movie);
                        }
                        SearchActivity.this.searchAdapter.notifyDataSetChanged();
                        Log.d("lol", "success");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("lol", "failure");
                        // TODO: Handle error

                    }
                });
        ApiFetch.getInstance(this).addToRequestQueue(jsonObjectRequest);
        this.page++;
        Log.d("lol", "Load next page (method)");
    }
}