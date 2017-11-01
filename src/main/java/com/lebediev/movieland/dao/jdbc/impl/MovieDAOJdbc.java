package com.lebediev.movieland.dao.jdbc.impl;

import com.lebediev.movieland.dao.MovieDAO;
import com.lebediev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.entity.MovieToGenre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDAOJdbc implements MovieDAO {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MovieRowMapper movieRowMapper;

    @Autowired
    GenreDAOJdbc genreDAOJdbc;

    @Override
    public List <Movie> getAllMovies() {
        log.info("Start query for getting all movies");
        long startTime = System.currentTimeMillis();

        String query = "SELECT movieId, movieNameRus, movieNameNative, date, description, rating, price, poster FROM movie ";

        log.info("Finish query for getting all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return jdbcTemplate.query(query, movieRowMapper);
    }

    public List<Movie> getRandomMovies(){
        log.info("Start getting random movies ");
        long startTime = System.currentTimeMillis();
        List<Movie> randomMovieList = new ArrayList<>();

        String query = "SELECT movieId, movieNameRus, movieNameNative, date, description, rating, price, poster FROM movie " +
                       "ORDER BY RAND() LIMIT 3";

       log.info("Finish getting random movies. It took {} ms", System.currentTimeMillis() - startTime);
         randomMovieList = jdbcTemplate.query(query, movieRowMapper);
        System.out.println("before " + randomMovieList);
        randomMovieList = enrichMovieByGenres(randomMovieList);
      //  System.out.println("enriched " + randomMovieList);
        return randomMovieList;
    }

    public List<Movie> enrichMovieByGenres (List<Movie> movieList){
        log.info("Start enriching movies by genres ");
        long startTime = System.currentTimeMillis();
        List<Movie> enrichedMovieList = new ArrayList<>();
        List<MovieToGenre> movieToGenreList = genreDAOJdbc.getMovieToGenreMappings();
        for(Movie movie : movieList){
            movie.setGenres(genreDAOJdbc.getGenresByMovieId(movieToGenreList,movie.getMovieId()));
        }

        log.info("Finish enriching movies by genres. It took {} ms", System.currentTimeMillis() - startTime);
       // return enrichedMovieList;
        return movieList;
    }

}
