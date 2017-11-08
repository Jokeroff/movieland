package com.lebediev.movieland.service;

import com.lebediev.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List <Movie> getAllMovies();
    List<Movie> getRandomMovies();
    List<Movie> getMoviesByGenreId(int genreId);
    List<Movie> getAllMovies(String orderBy, String direction);
    List<Movie> getMoviesByGenreId(int genreId, String orderBy, String direction);
}
