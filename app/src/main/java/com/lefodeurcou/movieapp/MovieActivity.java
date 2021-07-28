package com.lefodeurcou.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lefodeurcou.movieapp.models.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class MovieActivity extends AppCompatActivity {

    private TextView extras;
    private Movie movie = new Movie();
    private String input_movie;
    private JSONObject parsed_movie;
    private Boolean isSeeMoreClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        this.input_movie = "{" +
                "  \"Title\": \"Star Wars: Episode IV - A New Hope\"," +
                "  \"Year\": \"1977\"," +
                "  \"Rated\": \"PG\"," +
                "  \"Released\": \"25 May 1977\"," +
                "  \"Runtime\": \"121 min\"," +
                "  \"Genre\": \"Action, Adventure, Fantasy, Sci-Fi\"," +
                "  \"Director\": \"George Lucas\"," +
                "  \"Writer\": \"George Lucas\"," +
                "  \"Actors\": \"Mark Hamill, Harrison Ford, Carrie Fisher, Peter Cushing\"," +
                "  \"Plot\": \"The Imperial Forces, under orders from cruel Darth Vader, hold Princess Leia hostage in their efforts to quell the rebellion against the Galactic Empire. Luke Skywalker and Han Solo, captain of the Millennium Falcon, work together with the companionable droid duo R2-D2 and C-3PO to rescue the beautiful princess, help the Rebel Alliance and restore freedom and justice to the Galaxy.\"," +
                "  \"Language\": \"English\"," +
                "  \"Country\": \"USA\"," +
                "  \"Awards\": \"Won 6 Oscars. Another 52 wins & 28 nominations.\"," +
                "  \"Poster\": \"https://m.media-amazon.com/images/M/MV5BNzVlY2MwMjktM2E4OS00Y2Y3LWE3ZjctYzhkZGM3YzA1ZWM2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\"," +
                "  \"Ratings\": [" +
                "    {" +
                "      \"Source\": \"Internet Movie Database\"," +
                "      \"Value\": \"8.6/10\"" +
                "    }," +
                "    {" +
                "      \"Source\": \"Rotten Tomatoes\"," +
                "      \"Value\": \"92%\"" +
                "    }," +
                "    {" +
                "      \"Source\": \"Metacritic\"," +
                "      \"Value\": \"90/100\"" +
                "    }" +
                "  ]," +
                "  \"Metascore\": \"90\"," +
                "  \"imdbRating\": \"8.6\"," +
                "  \"imdbVotes\": \"1,181,083\"," +
                "  \"imdbID\": \"tt0076759\"," +
                "  \"Type\": \"movie\"," +
                "  \"DVD\": \"21 Sep 2004\"," +
                "  \"BoxOffice\": \"N/A\"," +
                "  \"Production\": \"20th Century Fox\", \"Website\": \"N/A\"," +
                "  \"Response\": \"True\"" +
                "}";

        try {
//            this.parsed_movie = new JSONObject(this.input_movie);
            this.getAssets().open("movie_one").;
            this.parsed_movie = new JSONObject();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        this.extras = findViewById(R.id.extras);
        this.extras.setText(extras.getCharSequence("id_movie"));

        try {
            this.movie.setTitle(this.parsed_movie.getString("Title"));
            this.movie.setDate(this.parsed_movie.getString("Released"));;
            this.movie.setDesc(this.parsed_movie.getString("Plot"));;
            this.movie.setGenre(this.parsed_movie.getString("Genre"));;
            this.movie.setReleased(this.parsed_movie.getString("Released"));
            this.movie.setDirector(this.parsed_movie.getString("Director"));
            this.movie.setActors(this.parsed_movie.getString("Actors"));
            this.movie.setAwards(this.parsed_movie.getString("Awards"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.updateUi();
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
    }
}