package com.lebediev.movieland.dao.jdbc.entity;

public class MovieToCountry {
    private int movieId;
    private int countryId;
    private String name;

    public MovieToCountry(int movieId, int countryId, String name) {
        this.movieId = movieId;
        this.countryId = countryId;
        this.name = name;
    }

    public MovieToCountry() {
    }

    public int getMovieId() {
        return movieId;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
