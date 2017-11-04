package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.dao.jdbc.mapper.MovieToGenreRowMapper;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MovieToGenreRowMapper movieToGenreRowMapper = new MovieToGenreRowMapper();

    @Value("${query.getMovieToGenreMappings}")
    private String queryGetMovieToGenreMappings;

    public List<MovieToGenre> getMovieToGenreMappings(){
        log.info("Start getting MovieToGenre mappings ");
        long startTime = System.currentTimeMillis();
        List<MovieToGenre> movieToGenreList = jdbcTemplate.query(queryGetMovieToGenreMappings, movieToGenreRowMapper);
        log.info("Finish getting MovieToGenre mappings. It took {} ms", System.currentTimeMillis() - startTime);
        return movieToGenreList;
    }
 }
