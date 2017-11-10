package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieDao {
    List<Movie> getAllMovies();

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenreId(int genreId);

    List<Movie> getAllMovies(Map<String, String> params);

    List<Movie> getMoviesByGenreId(int genreId, Map<String, String> params);
}
