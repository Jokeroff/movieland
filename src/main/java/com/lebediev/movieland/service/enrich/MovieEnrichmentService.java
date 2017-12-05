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

    public void enrichByGenres(Movie movie) {
        log.info("Start enriching movie by genres ");
        long startTime = System.currentTimeMillis();
        List<Integer> params = Arrays.asList(movie.getId());
        List<MovieToGenre> movieToGenreList = jenreDao.getMovieToGenreMappings(params);
        movie.setGenres(getGenresById(movieToGenreList, movie.getId()));
        log.info("Finish enriching movie by genres for id = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public void enrichByGenres(List<Movie> movieList) {
        log.info("Start enriching list of movies by genres ");
        long startTime = System.currentTimeMillis();
        List<Integer> params = getIds(movieList);
        List<MovieToGenre> movieToGenreList = jenreDao.getMovieToGenreMappings(params);
        for (Movie movie : movieList) {
            movie.setGenres(getGenresById(movieToGenreList, movie.getId()));
        }
        log.info("Finish enriching list of movies by genres for movie id's = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public void enrichByCountries(Movie movie) {
        log.info("Start enriching movie by countries ");
        long startTime = System.currentTimeMillis();
        List<Integer> params = Arrays.asList(movie.getId());
        List<MovieToCountry> movieToCountryList = countryDao.getMovieToCountryMappings(params);
        movie.setCountries(getCountriesById(movieToCountryList, movie.getId()));
        log.info("Finish enriching movie by countries for id = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public void enrichByCountries(List<Movie> movieList) {
        log.info("Start enriching list of movies by countries ");
        long startTime = System.currentTimeMillis();
        List<Integer> params = getIds(movieList);
        List<MovieToCountry> movieToCountryList = countryDao.getMovieToCountryMappings(params);
        for (Movie movie : movieList) {
            movie.setCountries(getCountriesById(movieToCountryList, movie.getId()));
        }
        log.info("Finish enriching list of movies by countries for id's = {}. It took {} ms", params, System.currentTimeMillis() - startTime);
    }

    public List<Genre> getGenresById(List<MovieToGenre> movieToGenreList, int id) {
        log.info("Start getting genres by id = {}", id);
        long startTime = System.currentTimeMillis();
        List<Genre> genreList = new ArrayList<>();

        for (MovieToGenre movieToGenre : movieToGenreList) {
            if (movieToGenre.getMovieId() == id) {
                genreList.add(new Genre(movieToGenre.getGenreId(), movieToGenre.getGenreName()));
            }
        }
        log.info("Finish getting genres by id = {}. It took {} ms", id, System.currentTimeMillis() - startTime);
        return genreList;
    }

    public List<Country> getCountriesById(List<MovieToCountry> movieToCountryList, int id) {
        log.info("Start getting countries by id = {}", id);
        long startTime = System.currentTimeMillis();
        List<Country> countryList = new ArrayList<>();

        for (MovieToCountry movieToCountry : movieToCountryList) {
            if (movieToCountry.getMovieId() == id) {
                countryList.add(new Country(movieToCountry.getCountryId(), movieToCountry.getName()));
            }
        }
        log.info("Finish getting countries by id = {}. It took {} ms", id, System.currentTimeMillis() - startTime);
        return countryList;
    }

    public void enrichByReviews(Movie movie) {
        log.info("Start enriching movie by reviews ");
        long startTime = System.currentTimeMillis();
        List<Review> reviewList = reviewDao.getReviewById(movie.getId());
        movie.setReviews(reviewList);
        log.info("Finish enriching movie by reviews. It took {} ms", System.currentTimeMillis() - startTime);
    }

    private List<Integer> getIds(List<Movie> movieList) {
        List<Integer> ids = new ArrayList<>();
        for (Movie movie : movieList) {
            ids.add(movie.getId());
        }
        return ids;
    }

}
