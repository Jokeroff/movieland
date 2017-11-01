package com.lebediev.movieland.entity;

public class MovieToGenre {
    private int movieId;
    private int genreId;
    private String genreName;

    public int getMovieId() {
        return movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public MovieToGenre(int movieId, int genreId, String genreName) {
        this.movieId = movieId;
        this.genreId = genreId;
        this.genreName = genreName;
    }
    public  MovieToGenre(){}
}
