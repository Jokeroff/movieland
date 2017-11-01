package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.Movie;

import java.util.List;

public interface MovieDAO {
    List <Movie> getAllMovies();
    List<Movie> getRandomMovies();
    List<Movie> enrichMovieByGenres (List<Movie> movieList);
}
