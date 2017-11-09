package com.lebediev.movieland.service;

import com.lebediev.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List <Movie> getAllMovies();
    List<Movie> getRandomMovies();
    List<Movie> getMoviesByGenreId(int genreId);
}
