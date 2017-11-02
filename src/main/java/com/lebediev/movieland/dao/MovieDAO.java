package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List <Movie> getAllMovies();
    List<Movie> getRandomMovies();
}
