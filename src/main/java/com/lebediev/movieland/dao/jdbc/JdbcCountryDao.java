package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.CountryDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import com.lebediev.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.lebediev.movieland.dao.jdbc.mapper.MovieToCountryRowMapper;
import com.lebediev.movieland.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JdbcCountryDao implements CountryDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final MovieToCountryRowMapper MOVIE_TO_COUNTRY_ROW_MAPPER = new MovieToCountryRowMapper();
    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    @Value("${query.getMovieToCountryMappings}")
    private String queryGetMovieToCountryMappings;
    @Value("${query.getAllCountries}")
    private String queryGetAll;

    public List <MovieToCountry> getMovieToCountryMappings(List <Integer> ids) {
        log.info("Start getting MovieToCountry mappings ");
        long startTime = System.currentTimeMillis();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", ids);
        List <MovieToCountry> movieToCountryList = jdbcTemplate.query(queryGetMovieToCountryMappings, params, MOVIE_TO_COUNTRY_ROW_MAPPER);
        log.info("Finish getting MovieToCountry mappings. It took {} ms", System.currentTimeMillis() - startTime);
        return movieToCountryList;
    }

    public List<Country> getAll(){
        log.info("Start getting all countries");
        long startTime = System.currentTimeMillis();
        List<Country> countryList = jdbcTemplate.query(queryGetAll, COUNTRY_ROW_MAPPER);
        log.info("Finish getting all countries. It took {} ms", System.currentTimeMillis() - startTime);
        return countryList;
    }
}
