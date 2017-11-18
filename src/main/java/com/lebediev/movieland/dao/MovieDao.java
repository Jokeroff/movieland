package com.lebediev.movieland.dao;

import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;

import java.util.List;



public interface MovieDao {

    List <Movie> getRandomMovies();

    List <Movie> getAll(SortParams params);

    List <Movie> getMoviesByGenreId(int genreId, SortParams params);

    Movie getMovieById(int id);
}
