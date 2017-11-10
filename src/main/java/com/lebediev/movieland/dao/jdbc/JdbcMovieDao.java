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
import java.util.Set;

@Repository
public class JdbcMovieDao implements MovieDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MovieEnrichmentService movieEnrichmentService;

    private MovieRowMapper movieRowMapper = new MovieRowMapper();

    private String orderBy;
    private String orderDirection;

    @Value("${query.getAllMovies}")
    private String queryGetAllMovies;
    @Value("${query.getRandomMovies}")
    private String queryGetRandomMovies;
    @Value("${query.getMoviesByGenreId}")
    private String queryGetMoviesByGenreId;

    @Override
    public List<Movie> getRandomMovies() {
        log.info("Start getting random movies ");
        long startTime = System.currentTimeMillis();
        List<Movie> randomMovieList = jdbcTemplate.query(queryGetRandomMovies, movieRowMapper);
        log.info("Finish getting random movies. It took {} ms", System.currentTimeMillis() - startTime);
        movieEnrichmentService.enrichMovieByGenres(randomMovieList);
        movieEnrichmentService.enrichMovieByCountries(randomMovieList);
        return randomMovieList;
    }

    @Override
    public List<Movie> getAllMovies(Map<String, String> params) {
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Map.Entry<String, String> entrySet : set) {
            orderBy = entrySet.getKey();
            orderDirection = entrySet.getValue();
        }

        log.info("Start query get all movies order by {} {}", orderBy, orderDirection);
        long startTime = System.currentTimeMillis();
        String queryGetAllMoviesOrdered = queryGetAllMovies;
        if (orderBy != null && orderDirection != null) {
            queryGetAllMoviesOrdered = queryGetAllMoviesOrdered + " ORDER BY " + orderBy + " " + orderDirection;
        }
        List<Movie> allMoviesOrderedList = jdbcTemplate.query(queryGetAllMoviesOrdered, movieRowMapper);
        log.info("Finish query get all movies order by {} {}. It took {} ms", orderBy, orderDirection, System.currentTimeMillis() - startTime);
        return allMoviesOrderedList;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId, Map<String, String> params) {
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Map.Entry<String, String> entrySet : set) {
            orderBy = entrySet.getKey();
            orderDirection = entrySet.getValue();
        }
        log.info("Start getting movies by genreId = {} order by {} {}", genreId, orderBy, orderDirection);
        long startTime = System.currentTimeMillis();
        String queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId;
        if (orderBy != null && orderDirection != null) {
            queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreIdOrdered + " ORDER BY " + orderBy + " " + orderDirection;
        }
        List<Movie> moviesByGenreOrderedList = jdbcTemplate.query(queryGetMoviesByGenreIdOrdered, movieRowMapper, genreId);
        log.info("Finish getting movies by genreId = {} order by {} {}. It took {} ms", genreId, orderBy, orderDirection, System.currentTimeMillis() - startTime);
        return moviesByGenreOrderedList;
    }

}


