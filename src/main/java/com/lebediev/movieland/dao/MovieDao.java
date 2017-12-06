package com.lebediev.movieland.dao;

import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;

import java.util.List;



public interface MovieDao {

    List <Movie> getRandomMovies();

    List <Movie> getAll(SortParams params);

    List <Movie> getMoviesByGenreId(int genreId, SortParams params);

    Movie getMovieById(int id);

    void add(MovieDtoForUpdate movie);

    void update(MovieDtoForUpdate movie);

    void addRatings(List<MovieRating> movieRatingList);

    List<MovieRating> getRatings();

    List<Movie> searchByTitle(String title);
}
