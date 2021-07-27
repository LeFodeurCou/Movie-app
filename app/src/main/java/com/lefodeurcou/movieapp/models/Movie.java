package com.lefodeurcou.movieapp.models;

import java.util.ArrayList;

public class Movie
{
    private CharSequence title;
    private CharSequence desc;
    private CharSequence genre;
    private CharSequence Director;
    private ArrayList<CharSequence> Actors;
    private ArrayList<CharSequence> Awards;
    private int img;

    public Movie(CharSequence title, CharSequence desc, int img)
    {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public CharSequence getTitle()
    {
        return this.title;
    }

    public CharSequence getDesc()
    {
        return this.desc;
    }

    public int getImg()
    {
        return this.img;
    }


}
