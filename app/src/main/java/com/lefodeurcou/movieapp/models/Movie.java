package com.lefodeurcou.movieapp.models;

import java.util.ArrayList;

public class Movie
{
    private String title;
    private String desc;
    private String genre;
    private String date;
    private String director;
    private String released;
    private String actors;
    private String awards;
    private int img;

    public Movie()
    {

    }

    public Movie(String title, String desc, int img)
    {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public int getImg()
    {
        return this.img;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getReleased() {
        return released;
    }

    public String getActors() {
        return actors;
    }

    public String getAwards() {
        return awards;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
