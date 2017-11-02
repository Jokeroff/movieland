package com.lebediev.movieland.dao.jdbc.impl;

import com.lebediev.movieland.dao.CountryDAO;
import com.lebediev.movieland.dao.jdbc.mapper.MovieToCountryRowMapper;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.MovieToCountry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryDAOJdbc implements CountryDAO {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MovieToCountryRowMapper movieToCountryRowMapper;

    public List<MovieToCountry> getMovieToCountryMappings(){
        log.info("Start getting MovieToCountry mappings ");
        long startTime = System.currentTimeMillis();
        String query = "SELECT countryId, countryName, movieId FROM country \n" +
                "JOIN movie2country USING (countryId)";
        log.info("Finish getting MovieToCountry mappings. It took {} ms", System.currentTimeMillis() - startTime);
        return jdbcTemplate.query(query, movieToCountryRowMapper);
    }

    public List<Country> getCountriesByMovieId(List<MovieToCountry> movieToCountryList, int movieId){
        List<Country> countryList = new ArrayList <>();

        for (MovieToCountry movieToCountry : movieToCountryList){
            if (movieToCountry.getMovieId() == movieId){
                countryList.add(new Country(movieToCountry.getCountryId(), movieToCountry.getCountryName()));
            }
        }
        return countryList;
    }
}
