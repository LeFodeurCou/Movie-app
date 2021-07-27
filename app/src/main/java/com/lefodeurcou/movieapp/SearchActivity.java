package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lefodeurcou.movieapp.models.Movie;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private Button home;
    private TextView extras;
    private ScrollView movie_list;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.movies.add(new Movie("Title1", "Desc1", R.drawable.lor));
        this.movies.add(new Movie("Title2", "Desc2", R.drawable.doctorwho));
        this.movies.add(new Movie("Title3", "Desc3", R.drawable.lor));
        this.movies.add(new Movie("Title4", "Desc4", R.drawable.doctorwho));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        this.extras = findViewById(R.id.extras);
        this.extras.setText(extras.getCharSequence("msg"));

        this.movie_list = findViewById(R.id.movie_list);
        LinearLayout wrapper = new LinearLayout(getApplicationContext());
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setLayoutParams(new LinearLayout
                .LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        this.movie_list.addView(wrapper);
        int size = this.movies.size();
        for (int i = 0; i < size; i++)
        {
            TextView test_movie = new TextView(getApplicationContext());
            test_movie.setText(this.movies.get(i).getTitle());
            wrapper.addView(this.createMovieRow(this.movies.get(i)));
        }

        this.home = findViewById(R.id.home);
        this.home.setOnClickListener(v -> finish()
        );
    }

    private LinearLayout createMovieRow(Movie movie)
    {
        LinearLayout row = new LinearLayout(getApplicationContext());
        row.setOrientation(LinearLayout.HORIZONTAL);

        ImageView img = new ImageView(getApplicationContext());
        img.setImageResource(movie.getImg());
        img.setLayoutParams(new LinearLayout
                .LayoutParams(
                200 * (int) getResources().getDisplayMetrics().density,
                200 * (int) getResources().getDisplayMetrics().density
        ));
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
}