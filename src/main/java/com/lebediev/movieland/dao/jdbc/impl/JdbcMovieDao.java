package com.lebediev.movieland.dao.jdbc.impl;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.enrich.MovieEnrichment;
import com.lebediev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.lebediev.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    MovieEnrichment movieEnrichment;

    MovieRowMapper movieRowMapper = new MovieRowMapper();

    @Override
    public List<Movie> getAllMovies() {
        log.info("Start query for getting all movies");
        long startTime = System.currentTimeMillis();

        String query = "SELECT movieId, movieNameRus, movieNameNative, date, description, rating, price, poster FROM movie ";

        log.info("Finish query for getting all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return jdbcTemplate.query(query, movieRowMapper);
    }

    public List<Movie> getRandomMovies() {
        log.info("Start getting random movies ");
        long startTime = System.currentTimeMillis();
        List<Movie> randomMovieList = new ArrayList<>();

        String query = "SELECT movieId, movieNameRus, movieNameNative, date, description, rating, price, poster FROM movie " +
                        "ORDER BY RAND() LIMIT 3";

        log.info("Finish getting random movies. It took {} ms", System.currentTimeMillis() - startTime);
        randomMovieList = jdbcTemplate.query(query, movieRowMapper);
        movieEnrichment.enrichMovieByGenres(randomMovieList);
        movieEnrichment.enrichMovieByCountries(randomMovieList);
        return randomMovieList;
    }


}


