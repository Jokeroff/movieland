package com.lebediev.movieland.dao;

import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import com.lebediev.movieland.entity.Country;

import java.util.List;


public interface CountryDao {
    List <MovieToCountry> getMovieToCountryMappings(List <Integer> iDs);
    List<Country> getAll();
}
