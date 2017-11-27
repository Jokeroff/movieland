package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;

import java.util.List;

public interface MovieService {

    List <Movie> getRandomMovies();

    List <Movie> getAllMovies(SortParams params);

    List <Movie> getMoviesByGenreId(int genreId, SortParams params);

    Movie getMovieById(int id);

    void add(MovieDtoForUpdate movie);

    void update(MovieDtoForUpdate movie);

}
