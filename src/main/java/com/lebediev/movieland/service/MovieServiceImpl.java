package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.service.ratings.RatingService;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;
    @Autowired
    private RatingService ratingService;

    @Override
    public List<Movie> getRandomMovies() {
        List<Movie> movieList = movieDao.getRandomMovies();
        ratingService.enrichByRatings(movieList);
        return movieList;
    }

    @Override
    public List<Movie> getAllMovies(SortParams params) {
        List<Movie> movieList = movieDao.getAll(params);
        ratingService.enrichByRatings(movieList);
        return movieList;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId, SortParams params) {
        List<Movie> movieList = movieDao.getMoviesByGenreId(genreId, params);
        ratingService.enrichByRatings(movieList);
        return movieList;
    }

    @Override
    public Movie getMovieById(int id) {
        Movie movie = movieDao.getMovieById(id);
        ratingService.enrichByRatings(movie);
        return movie;
    }

    @Override
    public void add(MovieDtoForUpdate movie) {
        movieDao.add(movie);
    }

    @Override
    public void update(MovieDtoForUpdate movie) {
        movieDao.update(movie);
    }

    @Override
    public void addRating(MovieRating movieRating) {
        ratingService.addRating(movieRating);
    }

    @Override
    public void addRatings(List<MovieRating> movieRatingList) {
        movieDao.addRatings(movieRatingList);
    }

    @Override
    public List<MovieRating> getRatings() {
        return movieDao.getRatings();
    }

    @Override
    public double getRating(int movieId) {
        return ratingService.getRating(movieId);
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        return movieDao.searchByTitle(title);
    }
}
