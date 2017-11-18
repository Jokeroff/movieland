package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.lebediev.movieland.dao.jdbc.mapper.MovieToGenreRowMapper;
import com.lebediev.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JdbcGenreDao implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final MovieToGenreRowMapper movieToGenreRowMapper = new MovieToGenreRowMapper();
    private final GenreRowMapper genreRowMapper = new GenreRowMapper();

    @Value("${query.getMovieToGenreMappings}")
    private String queryGetMovieToGenreMappings;

    @Value("${query.getAllGenres}")
    private String queryGetAllGenres;

    public List <MovieToGenre> getMovieToGenreMappings(List <Integer> ids) {
        log.info("Start getting MovieToGenre mappings ");
        long startTime = System.currentTimeMillis();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", ids);
        List <MovieToGenre> movieToGenreList = jdbcTemplate.query(queryGetMovieToGenreMappings, params, movieToGenreRowMapper);
        log.info("Finish getting MovieToGenre mappings. It took {} ms", System.currentTimeMillis() - startTime);
        return movieToGenreList;
    }

    public List <Genre> getAll() {
        log.info("Start getting all genres ");
        long startTime = System.currentTimeMillis();
        List <Genre> genreList = jdbcTemplate.query(queryGetAllGenres, genreRowMapper);
        log.info("Finish getting all genres. It took {} ms", System.currentTimeMillis() - startTime);
        return genreList;
    }
}
