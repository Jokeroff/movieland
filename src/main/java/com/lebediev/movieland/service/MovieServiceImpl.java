package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Override
    public List <Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }
    @Override
    public List<Movie> getRandomMovies(){
        return movieDao.getRandomMovies();
    }
    @Override
    public List<Movie> getMoviesByGenreId(int genreId) {
        return movieDao.getMoviesByGenreId(genreId);
    }

    @Override
    public List <Movie> getAllMovies(String orderBy,String direction) {
        return movieDao.getAllMovies(orderBy, direction);
    }

    @Override
    public List <Movie> getMoviesByGenreId(int genreId, String orderBy,String direction) {
        return movieDao.getMoviesByGenreId(genreId, orderBy, direction);
    }

}
