package com.example.games;


import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private String Name,Publisher,Platforms,Modes,Genres,ReleaseDate,Series,Info,dbID;
    private  ArrayList<Review> Reviews;
    private double AvgRating=0;
    private ArrayList<GamePrice> Prices;




    public Game(String name, String publisher, String platforms, String modes, String genres, String releaseDate, String series, String info, ArrayList<GamePrice>prices) {
        Name = name;
        Publisher = publisher;
        Platforms = platforms;
        Modes = modes;
        Genres = genres;
        ReleaseDate = releaseDate;
        Series = series;
        Info = info;
        Reviews=new ArrayList<>();
        Prices=prices;
        AvgRating = 0.0;



    }
    public Game(){}

    public void setDbID(String dbID) {
        this.dbID = dbID;
    }

    public String getDbID() {
        return dbID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getPlatforms() {
        return Platforms;
    }

    public void setPlatforms(String platforms) {
        Platforms = platforms;
    }

    public String getModes() {
        return Modes;
    }

    public void setModes(String modes) {
        Modes = modes;
    }

    public String getGenres() {
        return Genres;
    }

    public void setGenres(String genres) {
        Genres = genres;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public double getAvgRating() {
        return AvgRating;
    }

    public void setAvgRating(double avgRating) {
        AvgRating = avgRating;
    }

    public ArrayList<GamePrice> getPrices() {
        return Prices;
    }

    public void setPrices(ArrayList<GamePrice> prices) {
        Prices = prices;
    }

    public ArrayList<Review> getReviews() {
        return Reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.Reviews = reviews;
    }



    @Override
    public String toString() {
        return "Game{" +
                "Name='" + Name + '\'' +
                ", Publisher='" + Publisher + '\'' +
                ", Platforms='" + Platforms + '\'' +
                ", Modes='" + Modes + '\'' +
                ", Genres='" + Genres + '\'' +
                ", ReleaseDate='" + ReleaseDate + '\'' +
                ", Series='" + Series + '\'' +
                ", Info='" + Info + '\'' +
                ", Reviews=" + Reviews +
                ", AvgRating=" + AvgRating +
                ", Prices=" + Prices +


                '}';
    }


}
