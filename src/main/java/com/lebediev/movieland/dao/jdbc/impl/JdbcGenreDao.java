package com.lebediev.movieland.dao.jdbc.impl;

import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.dao.jdbc.mapper.MovieToGenreRowMapper;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MovieToGenreRowMapper movieToGenreRowMapper = new MovieToGenreRowMapper();

    public List<MovieToGenre> getMovieToGenreMappings(){
        log.info("Start getting MovieToGenre mappings ");
        long startTime = System.currentTimeMillis();
        String query = "SELECT genreId, genreName, movieId FROM genre " +
                       "JOIN movie2genre USING (genreId)";
        log.info("Finish getting MovieToGenre mappings. It took {} ms", System.currentTimeMillis() - startTime);
        return jdbcTemplate.query(query, movieToGenreRowMapper);
    }
 }
