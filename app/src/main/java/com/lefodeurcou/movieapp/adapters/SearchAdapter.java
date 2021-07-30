package com.lefodeurcou.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lefodeurcou.movieapp.MovieActivity;
import com.lefodeurcou.movieapp.R;
import com.lefodeurcou.movieapp.SearchActivity;
import com.lefodeurcou.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout search_item;
        private final ImageView search_img;
        private final TextView search_title;
        private final TextView search_date;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.search_item = (LinearLayout) view.findViewById(R.id.search_item);
            this.search_img = (ImageView) view.findViewById(R.id.search_img);
            this.search_title = (TextView) view.findViewById(R.id.search_title);
            this.search_date = (TextView) view.findViewById(R.id.search_date);
        }

        public LinearLayout getSearch_item() {
            return search_item;
        }

        public ImageView getSearch_img() {
            return search_img;
        }

        public TextView getSearch_title() {
            return search_title;
        }

        public TextView getSearch_date() {
            return search_date;
        }
    }

    public SearchAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_search_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull SearchAdapter.ViewHolder holder, int position) {
        Picasso.get()
                .load(this.movies.get(position).getImgUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.getSearch_img());
        holder.getSearch_title().setText(String.valueOf(SearchAdapter.this.movies.get(position).getTitle()));
        holder.getSearch_date().setText(String.valueOf(SearchAdapter.this.movies.get(position).getReleased()));
        holder.getSearch_item().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MovieActivity.class);
                myIntent.putExtra("imdb", SearchAdapter.this.movies.get(position).getIMDb());
                context.startActivity(myIntent);
            }
        });
        if (this.context.getClass() == SearchActivity.class && position == this.movies.size() - 1)
            ((SearchActivity) this.context).loadPage();
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }
}