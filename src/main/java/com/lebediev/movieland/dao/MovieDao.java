package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List <Movie> getAllMovies();
    List<Movie> getRandomMovies();
    List<Movie> getMoviesByGenreId(int genreId);
    List<Movie> getAllMovies(String orderBy, String direction);
    List<Movie> getMoviesByGenreId(int genreId, String orderBy, String direction);
}
