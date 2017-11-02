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

    public List <Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    public List<Movie> getRandomMovies(){
        return movieDao.getRandomMovies();
    }
}
