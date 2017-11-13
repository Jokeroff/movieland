package com.lebediev.movieland.dao;

import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;

import java.util.List;

public interface CountryDao {
    List<MovieToCountry> getMovieToCountryMappings();
}
