package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.MovieToCountry;

import java.util.List;

public interface CountryDAO {
    List<MovieToCountry> getMovieToCountryMappings();
    List<Country> getCountriesByMovieId(List<MovieToCountry> movieToCountryList, int movieId);
}
