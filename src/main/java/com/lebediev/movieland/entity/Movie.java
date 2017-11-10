package com.lebediev.movieland.entity;

import java.util.List;

public class Movie {
    private int movieId;
    private String movieNameRus;
    private String movieNameNative;
    private int date;
    private String description;
    private double rating;
    private double price;
    private List<Genre> genres;
    private List<Country> countries;
    private String poster;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieNameRus() {
        return movieNameRus;
    }

    public String getMovieNameNative() {
        return movieNameNative;
    }

    public int getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setMovieNameRus(String movieNameRus) {
        this.movieNameRus = movieNameRus;
    }

    public void setMovieNameNative(String movieNameNative) {
        this.movieNameNative = movieNameNative;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Movie(int movieId, String movieNameRus, String movieNameNative, int date, String description, double rating, double price, List<Genre> genres, List<Country> countries, String poster) {
        this.movieId = movieId;
        this.movieNameRus = movieNameRus;
        this.movieNameNative = movieNameNative;
        this.date = date;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.genres = genres;
        this.countries = countries;
        this.poster = poster;
    }

    public Movie() {
    }

}
