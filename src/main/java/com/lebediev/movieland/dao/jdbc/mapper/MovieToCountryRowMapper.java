package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieToCountryRowMapper implements RowMapper<MovieToCountry> {
    @Override
    public MovieToCountry mapRow(ResultSet resultSet, int i) throws SQLException {
        MovieToCountry movieToCountry = new MovieToCountry();
        movieToCountry.setMovieId(resultSet.getInt("movieId"));
        movieToCountry.setCountryId(resultSet.getInt("countryId"));
        movieToCountry.setCountryName(resultSet.getString("countryName"));
        return movieToCountry;
    }
}
