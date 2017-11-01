package com.lebediev.movieland.dao.jdbc.impl;

import com.lebediev.movieland.dao.MovieDAO;
import com.lebediev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.lebediev.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDAOJdbc implements MovieDAO {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MovieRowMapper movieRowMapper;

    @Override
    public List<Movie> getAllMovies() {
        log.info("Start query for getting all movies");
        long startTime = System.currentTimeMillis();

        String query = "SELECT movieId, movieNameRus, movieNameNative, date, description, rating, price, poster FROM movie ";

        log.info("Finish query for getting all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return jdbcTemplate.query(query, movieRowMapper);
    }
}
