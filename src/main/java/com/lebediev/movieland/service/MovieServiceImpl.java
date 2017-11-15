package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Override
    public List <Movie> getRandomMovies() {
        return movieDao.getRandomMovies();
    }

    @Override
    public List <Movie> getAllMovies(SortParams params) {
        return movieDao.getAll(params);
    }

    @Override
    public List <Movie> getMoviesByGenreId(int genreId, SortParams params) {
        return movieDao.getMoviesByGenreId(genreId, params);
    }

    @Override
    public Movie getMovieById(int movieId) {
        return movieDao.getMovieById(movieId);
    }


}
