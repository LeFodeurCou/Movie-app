package com.lefodeurcou.movieapp.models;

import java.util.ArrayList;

public class Movie
{
    private String IMDb;
    private String title;
    private String desc;
    private String genre;
    private String date;
    private String director;
    private String released;
    private String actors;
    private String awards;
    private String imgUrl;

    public Movie()
    {

    }

    public Movie(String title, String desc, String imgUrl)
    {
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
    }

    public String getIMDb() {
        return IMDb;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public String getImgUrl() {
        return imgUrl;
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

    public void setIMDb(String IMDb) {
        this.IMDb = IMDb;
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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
