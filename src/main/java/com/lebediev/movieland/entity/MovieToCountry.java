package com.lebediev.movieland.entity;

public class MovieToCountry {
    private int movieId;
    private int countryId;
    private String countryName;

    public int getMovieId() {
        return movieId;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
