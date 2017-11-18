package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
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
    public List <Movie> getAll(SortParams params) {
        log.info("Start query get all movies with params {}", params);
        long startTime = System.currentTimeMillis();
        String queryGetAllMoviesOrdered = queryGetAllMovies;
            if(params.getOrderBy() != null) {
               queryGetAllMoviesOrdered = queryGetAllMovies + " ORDER BY " +  params.getOrderBy() + " " + params.getSortDirection();
            }
        List <Movie> allMoviesOrderedList = jdbcTemplate.query(queryGetAllMoviesOrdered, movieRowMapper);
        log.info("Finish query get all movies with params {}. It took {} ms", params, System.currentTimeMillis() - startTime);
        return allMoviesOrderedList;
    }

    @Override
    public List <Movie> getMoviesByGenreId(int genreId, SortParams params) {
        log.info("Start getting movies by genreId = {} with params {}", genreId, params);
        long startTime = System.currentTimeMillis();
        String queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId;
            if(params.getOrderBy() != null) {
                queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId + " ORDER BY " + params.getOrderBy() + " " + params.getSortDirection();
            }
        List <Movie> moviesByGenreOrderedList = jdbcTemplate.query(queryGetMoviesByGenreIdOrdered, movieRowMapper, genreId);
        log.info("Finish getting movies by genreId = {} with params {}. It took {} ms", genreId, params, System.currentTimeMillis() - startTime);
        return moviesByGenreOrderedList;
    }

    @Override
    public Movie getMovieById(int id) {
        log.info("Start getting movies by id = {} ", id);
        long startTime = System.currentTimeMillis();
        Movie movie = jdbcTemplate.queryForObject(queryGetMoviesById, movieRowMapper, id);
        movieEnrichmentService.enrichMovieByReviews(movie);
        movieEnrichmentService.enrichMovieByCountries(movie);
        movieEnrichmentService.enrichMovieByGenres(movie);
        log.info("Finish getting movies by id = {}. It took {} ms", id, System.currentTimeMillis() - startTime);
        return movie;
    }


}


