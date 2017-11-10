package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.CountryDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import com.lebediev.movieland.dao.jdbc.mapper.MovieToCountryRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MovieToCountryRowMapper movieToCountryRowMapper = new MovieToCountryRowMapper();

    @Value("${query.getMovieToCountryMappings}")
    private String queryGetMovieToCountryMappings;

    public List<MovieToCountry> getMovieToCountryMappings() {
        log.info("Start getting MovieToCountry mappings ");
        long startTime = System.currentTimeMillis();
        List<MovieToCountry> movieToCountryList = jdbcTemplate.query(queryGetMovieToCountryMappings, movieToCountryRowMapper);
        log.info("Finish getting MovieToCountry mappings. It took {} ms", System.currentTimeMillis() - startTime);
        return movieToCountryList;
    }
}
