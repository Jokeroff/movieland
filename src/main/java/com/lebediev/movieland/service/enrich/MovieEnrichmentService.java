package com.lebediev.movieland.service.enrich;

import com.lebediev.movieland.dao.CountryDao;
import com.lebediev.movieland.dao.GenreDao;
import com.lebediev.movieland.dao.ReviewDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieEnrichmentService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private GenreDao jenreDao;

    @Autowired
    private ReviewDao reviewDao;

    public void enrichMovieByGenres(Movie movie) {
        log.info("Start enriching movie by genres ");
        long startTime = System.currentTimeMillis();
        List <Integer> params = Arrays.asList(movie.getMovieId());
        List <MovieToGenre> movieToGenreList = jenreDao.getMovieToGenreMappings(params);
        movie.setGenres(getGenresByMovieId(movieToGenreList, movie.getMovieId()));
        log.info("Finish enriching movie by genres for movieId = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public void enrichMovieByGenres(List <Movie> movieList) {
        log.info("Start enriching list of movies by genres ");
        long startTime = System.currentTimeMillis();
        List <Integer> params = getMovieIds(movieList);
        List <MovieToGenre> movieToGenreList = jenreDao.getMovieToGenreMappings(params);
        for (Movie movie : movieList) {
            movie.setGenres(getGenresByMovieId(movieToGenreList, movie.getMovieId()));
        }
        log.info("Finish enriching list of movies by genres for movie id's = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public void enrichMovieByCountries(Movie movie) {
        log.info("Start enriching movie by countries ");
        long startTime = System.currentTimeMillis();
        List <Integer> params = Arrays.asList(movie.getMovieId());
        List <MovieToCountry> movieToCountryList = countryDao.getMovieToCountryMappings(params);
        movie.setCountries(getCountriesByMovieId(movieToCountryList, movie.getMovieId()));
        log.info("Finish enriching movie by countries for movieId = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public void enrichMovieByCountries(List <Movie> movieList) {
        log.info("Start enriching list of movies by countries ");
        long startTime = System.currentTimeMillis();
        List <Integer> params = getMovieIds(movieList);
        List <MovieToCountry> movieToCountryList = countryDao.getMovieToCountryMappings(params);
        for (Movie movie : movieList) {
            movie.setCountries(getCountriesByMovieId(movieToCountryList, movie.getMovieId()));
        }
        log.info("Finish enriching list of movies by countries for movieId's = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public List <Genre> getGenresByMovieId(List <MovieToGenre> movieToGenreList, int movieId) {
        log.info("Start getting genres by movieId = {}", movieId);
        long startTime = System.currentTimeMillis();
        List <Genre> genreList = new ArrayList <>();

        for (MovieToGenre movieToGenre : movieToGenreList) {
            if (movieToGenre.getMovieId() == movieId) {
                genreList.add(new Genre(movieToGenre.getGenreId(), movieToGenre.getGenreName()));
            }
        }
        log.info("Finish getting genres by movieId = {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return genreList;
    }

    public List <Country> getCountriesByMovieId(List <MovieToCountry> movieToCountryList, int movieId) {
        log.info("Start getting countries by movieId = {}", movieId);
        long startTime = System.currentTimeMillis();
        List <Country> countryList = new ArrayList <>();

        for (MovieToCountry movieToCountry : movieToCountryList) {
            if (movieToCountry.getMovieId() == movieId) {
                countryList.add(new Country(movieToCountry.getCountryId(), movieToCountry.getCountryName()));
            }
        }
        log.info("Finish getting countries by movieId = {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return countryList;
    }

    public void enrichMovieByReviews(Movie movie) {
        log.info("Start enriching movie by reviews ");
        long startTime = System.currentTimeMillis();
        List <Review> reviewList = reviewDao.getReviewByMovieId(movie.getMovieId());
        movie.setReviews(reviewList);
        log.info("Finish enriching movie by reviews. It took {} ms", System.currentTimeMillis() - startTime);
    }

    private List <Integer> getMovieIds(List <Movie> movieList) {
        List <Integer> movieIds = new ArrayList <>();
        for (Movie movie : movieList) {
            movieIds.add(movie.getMovieId());
        }
        return movieIds;
    }


}
