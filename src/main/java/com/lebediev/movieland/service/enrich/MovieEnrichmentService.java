package com.lebediev.movieland.service.enrich;

import com.lebediev.movieland.dao.jdbc.JdbcCountryDao;
import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieEnrichmentService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcCountryDao jdbcCountryDao;

    @Autowired
    private JdbcGenreDao jdbcGenreDao;

    public void enrichMovieByGenres(List<Movie> movieList) {
        log.info("Start enriching movies by genres ");
        long startTime = System.currentTimeMillis();
        List<MovieToGenre> movieToGenreList = jdbcGenreDao.getMovieToGenreMappings();
        for (Movie movie : movieList) {
            movie.setGenres(getGenresByMovieId(movieToGenreList, movie.getMovieId()));
        }
        log.info("Finish enriching movies by genres. It took {} ms", System.currentTimeMillis() - startTime);
    }

    public void enrichMovieByCountries(List<Movie> movieList) {
        log.info("Start enriching movies by countries ");
        long startTime = System.currentTimeMillis();
        List<MovieToCountry> movieToCountryList = jdbcCountryDao.getMovieToCountryMappings();
        for (Movie movie : movieList) {
            movie.setCountries(getCountriesByMovieId(movieToCountryList, movie.getMovieId()));
        }
        log.info("Finish enriching movies by countries. It took {} ms", System.currentTimeMillis() - startTime);
    }

    public List<Genre> getGenresByMovieId(List<MovieToGenre> movieToGenreList, int movieId) {
        log.info("Start getting genres by movieId ");
        long startTime = System.currentTimeMillis();
        List<Genre> genreList = new ArrayList<>();

        for (MovieToGenre movieToGenre : movieToGenreList) {
            if (movieToGenre.getMovieId() == movieId) {
                genreList.add(new Genre(movieToGenre.getGenreId(), movieToGenre.getGenreName()));
            }
        }
        log.info("Finish getting genres by movieId. It took {} ms", System.currentTimeMillis() - startTime);
        return genreList;
    }

    public List<Country> getCountriesByMovieId(List<MovieToCountry> movieToCountryList, int movieId) {
        log.info("Start getting countries by movieId ");
        long startTime = System.currentTimeMillis();
        List<Country> countryList = new ArrayList<>();

        for (MovieToCountry movieToCountry : movieToCountryList) {
            if (movieToCountry.getMovieId() == movieId) {
                countryList.add(new Country(movieToCountry.getCountryId(), movieToCountry.getCountryName()));
            }
        }
        log.info("Finish getting countries by movieId. It took {} ms", System.currentTimeMillis() - startTime);
        return countryList;
    }
}
