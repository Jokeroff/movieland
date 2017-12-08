package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
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

    void addRating(MovieRating movieRating);

    void addRatings(List<MovieRating> movieRatingList);

    List<MovieRating> getRatings();

    double getRating(int movieId);

    List<Movie> searchByTitle(String title, int page);

}
