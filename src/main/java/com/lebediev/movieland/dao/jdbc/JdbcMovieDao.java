package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.service.enrich.MovieEnrichmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MovieEnrichmentService movieEnrichmentService;

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();

    @Value("${query.getAllMovies}")
    private String queryGetAllMovies;
    @Value("${query.getRandomMovies}")
    private String queryGetRandomMovies;
    @Value("${query.getMoviesByGenreId}")
    private String queryGetMoviesByGenreId;
    @Value("${query.getMoviesById}")
    private String queryGetMoviesById;

    @Override
    public List <Movie> getRandomMovies() {
        log.info("Start getting random movies ");
        long startTime = System.currentTimeMillis();
        List <Movie> randomMovieList = jdbcTemplate.query(queryGetRandomMovies, movieRowMapper);
        log.info("Finish getting random movies. It took {} ms", System.currentTimeMillis() - startTime);
        movieEnrichmentService.enrichMovieByGenres(randomMovieList);
        movieEnrichmentService.enrichMovieByCountries(randomMovieList);
        return randomMovieList;
    }

    @Override
    public List <Movie> getAll(Map <String, String> params) {
        log.info("Start query get all movies with params {}", params);
        long startTime = System.currentTimeMillis();
        String queryGetAllMoviesOrdered = queryGetAllMovies;
            for (Map.Entry <String, String> entry : params.entrySet()) {
                queryGetAllMoviesOrdered = queryGetAllMovies + " ORDER BY " + entry.getKey() + " " + entry.getValue();
            }
        List <Movie> allMoviesOrderedList = jdbcTemplate.query(queryGetAllMoviesOrdered, movieRowMapper);
        log.info("Finish query get all movies with params {}. It took {} ms", params, System.currentTimeMillis() - startTime);
        return allMoviesOrderedList;
    }

    @Override
    public List <Movie> getMoviesByGenreId(int genreId, Map <String, String> params) {
        log.info("Start getting movies by genreId = {} with params {}", genreId, params);
        long startTime = System.currentTimeMillis();
        String queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId;
            for (Map.Entry <String, String> entry : params.entrySet()) {
                queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId + " ORDER BY " + entry.getKey() + " " + entry.getValue();
            }
        List <Movie> moviesByGenreOrderedList = jdbcTemplate.query(queryGetMoviesByGenreIdOrdered, movieRowMapper, genreId);
        log.info("Finish getting movies by genreId = {} with params {}. It took {} ms", genreId, params, System.currentTimeMillis() - startTime);
        return moviesByGenreOrderedList;
    }

    @Override
    public Movie getMovieById(int movieId) {
        log.info("Start getting movies by id = {} ", movieId);
        long startTime = System.currentTimeMillis();
        Movie movie = jdbcTemplate.queryForObject(queryGetMoviesById, movieRowMapper, movieId);
        movieEnrichmentService.enrichMovieByReviews(movie);
        movieEnrichmentService.enrichMovieByCountries(movie);
        movieEnrichmentService.enrichMovieByGenres(movie);
        log.info("Finish getting movies by id = {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return movie;
    }


}


