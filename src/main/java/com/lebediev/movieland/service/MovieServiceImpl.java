package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> getRandomMovies() {
        return movieDao.getRandomMovies();
    }

    @Override
    public List<Movie> getAllMovies(Map<String, String> params) {
        return movieDao.getAllMovies(params);
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId, Map<String, String> params) {
        return movieDao.getMoviesByGenreId(genreId, params);
    }

}
