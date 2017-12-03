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
        return movieDao.getRandomMovies();
    }

    @Override
    public List<Movie> getAllMovies(SortParams params) {
        return movieDao.getAll(params);
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId, SortParams params) {
        return movieDao.getMoviesByGenreId(genreId, params);
    }

    @Override
    public Movie getMovieById(int id) {
        return movieDao.getMovieById(id);
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
}
