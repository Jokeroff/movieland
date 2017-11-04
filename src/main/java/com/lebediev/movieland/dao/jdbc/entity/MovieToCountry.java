package com.lebediev.movieland.dao.jdbc.entity;

public class MovieToCountry {
    private int movieId;
    private int countryId;
    private String countryName;

    public MovieToCountry(int movieId, int countryId, String countryName) {
        this.movieId = movieId;
        this.countryId = countryId;
        this.countryName = countryName;
    }
    public MovieToCountry(){}

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
