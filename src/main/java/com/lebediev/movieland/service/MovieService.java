package com.lebediev.movieland.service;

import com.lebediev.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {
    List<Movie> getAllMovies();

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenreId(int genreId);

    List<Movie> getAllMovies(Map<String, String> params);

    List<Movie> getMoviesByGenreId(int genreId, Map<String, String> params);
}
