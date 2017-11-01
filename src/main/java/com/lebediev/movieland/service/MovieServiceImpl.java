package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.MovieDAO;
import com.lebediev.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDAO movieDAO;

    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();

    }
}
