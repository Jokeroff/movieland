package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.enrich.MovieEnrichmentService;
import com.lebediev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.lebediev.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MovieEnrichmentService movieEnrichmentService;

    private MovieRowMapper movieRowMapper = new MovieRowMapper();

    @Value("${query.getAllMovies}")
    private String queryGetAllMovies;

    @Value("${query.getRandomMovies}")
    private String queryGetRandomMovies;

    @Override
    public List <Movie> getAllMovies() {
        log.info("Start query_get_all_movies for getting all movies");
        long startTime = System.currentTimeMillis();
        List <Movie> allMoviesList = jdbcTemplate.query(queryGetAllMovies, movieRowMapper);
        log.info("Finish query_get_all_movies for getting all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return allMoviesList;
    }

    public List <Movie> getRandomMovies() {
        log.info("Start getting random movies ");
        long startTime = System.currentTimeMillis();
        List <Movie> randomMovieList = jdbcTemplate.query(queryGetRandomMovies, movieRowMapper);
        log.info("Finish getting random movies. It took {} ms", System.currentTimeMillis() - startTime);
        movieEnrichmentService.enrichMovieByGenres(randomMovieList);
        movieEnrichmentService.enrichMovieByCountries(randomMovieList);
        return randomMovieList;
    }

}


