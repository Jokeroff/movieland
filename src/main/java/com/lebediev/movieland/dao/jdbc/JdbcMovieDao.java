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
    @Value("${query.getMoviesByGenreId}")
    private String queryGetMoviesByGenreId;

    @Override
    public List <Movie> getAllMovies() {
        log.info("Start query_get_all_movies for getting all movies");
        long startTime = System.currentTimeMillis();
        List <Movie> allMoviesList = jdbcTemplate.query(queryGetAllMovies, movieRowMapper);
        log.info("Finish query_get_all_movies for getting all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return allMoviesList;
    }

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
    public List<Movie> getMoviesByGenreId(int genreId){
        log.info("Start getting movies by genre ");
        long startTime = System.currentTimeMillis();
        List<Movie> moviesByGenreList = jdbcTemplate.query(queryGetMoviesByGenreId, movieRowMapper, genreId);
        log.info("Finish getting movies by genre. It took {} ms", System.currentTimeMillis() - startTime);
        return moviesByGenreList;
    }

    @Override
    public List <Movie> getAllMovies(String orderBy, String direction) {
        log.info("Start query get all movies order by {} {}", orderBy, direction);
        long startTime = System.currentTimeMillis();
        String queryGetAllMoviesOrdered = queryGetAllMovies + " ORDER BY " + orderBy + " " + direction;
        List <Movie> allMoviesOrderedList = jdbcTemplate.query(queryGetAllMoviesOrdered, movieRowMapper);
        log.info("Finish query get all movies order by {} {}. It took {} ms", orderBy, direction, System.currentTimeMillis() - startTime);
        return allMoviesOrderedList;
    }

    @Override
    public List <Movie> getMoviesByGenreId(int genreId, String orderBy, String direction) {
        log.info("Start getting movies by genreId = {} order by {} {}", genreId, orderBy, direction);
        long startTime = System.currentTimeMillis();
        String queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId + " ORDER BY " + orderBy + " " + direction;
        List<Movie> moviesByGenreOrderedList = jdbcTemplate.query(queryGetMoviesByGenreIdOrdered, movieRowMapper, genreId);
        log.info("Finish getting movies by genreId = {} order by {} {}. It took {} ms", genreId, orderBy, direction, System.currentTimeMillis() - startTime);
        return moviesByGenreOrderedList;
    }

}


