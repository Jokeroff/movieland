package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.dao.jdbc.mapper.MovieRatingRowMapper;
import com.lebediev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.enrich.MovieEnrichmentService;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Repository
public class JdbcMovieDao implements MovieDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MovieEnrichmentService movieEnrichmentService;
    @Autowired
    private AuthService authService;

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final MovieRatingRowMapper movieRatingRowMapper = new MovieRatingRowMapper();

    @Value("${query.getAllMovies}")
    private String queryGetAllMovies;
    @Value("${query.getRandomMovies}")
    private String queryGetRandomMovies;
    @Value("${query.getMoviesByGenreId}")
    private String queryGetMoviesByGenreId;
    @Value("${query.getMoviesById}")
    private String queryGetMoviesById;
    @Value("${query.addMovie}")
    private String queryAdd;
    @Value("${query.updateMovie}")
    private String queryUpdate;
    @Value("${query.addMovieCountry}")
    private String queryAddMovieCountry;
    @Value("${query.addMovieGenre}")
    private String queryAddMovieGenre;
    @Value("${query.addRating}")
    private String queryAddRating;
    @Value("${query.getRatings}")
    private String queryGetRatings;
    @Value("${query.searchByTitle}")
    private String querySearchByTitle;

    @Override
    public List<Movie> getRandomMovies() {
        log.info("Start getting random movies ");
        long startTime = System.currentTimeMillis();
        List<Movie> randomMovieList = jdbcTemplate.query(queryGetRandomMovies, movieRowMapper);
        log.info("Finish getting random movies. It took {} ms", System.currentTimeMillis() - startTime);
        movieEnrichmentService.enrichByGenres(randomMovieList);
        movieEnrichmentService.enrichByCountries(randomMovieList);
        return randomMovieList;
    }

    @Override
    public List<Movie> getAll(SortParams params) {
        log.info("Start query get all movies with params {}", params);
        long startTime = System.currentTimeMillis();
        String queryGetAllMoviesOrdered = queryGetAllMovies;
        if (params.getOrderBy() != null) {
            queryGetAllMoviesOrdered = queryGetAllMovies + " ORDER BY " + params.getOrderBy() + " " + params.getSortDirection();
        }
        List<Movie> allMoviesOrderedList = jdbcTemplate.query(queryGetAllMoviesOrdered, movieRowMapper);

        log.info("Finish query get all movies with params {}. It took {} ms", params, System.currentTimeMillis() - startTime);
        return allMoviesOrderedList;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId, SortParams params) {
        log.info("Start getting movies by genreId = {} with params {}", genreId, params);
        long startTime = System.currentTimeMillis();
        String queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId;
        if (params.getOrderBy() != null) {
            queryGetMoviesByGenreIdOrdered = queryGetMoviesByGenreId + " ORDER BY " + params.getOrderBy() + " " + params.getSortDirection();
        }
        List<Movie> moviesByGenreOrderedList = jdbcTemplate.query(queryGetMoviesByGenreIdOrdered, movieRowMapper, genreId);
        log.info("Finish getting movies by genreId = {} with params {}. It took {} ms", genreId, params, System.currentTimeMillis() - startTime);
        return moviesByGenreOrderedList;
    }

    @Override
    public Movie getMovieById(int id) {
        log.info("Start getting movies by id = {} ", id);
        long startTime = System.currentTimeMillis();
        Movie movie = jdbcTemplate.queryForObject(queryGetMoviesById, movieRowMapper, id);
        movieEnrichmentService.enrichByReviews(movie);
        movieEnrichmentService.enrichByCountries(movie);
        movieEnrichmentService.enrichByGenres(movie);
        log.info("Finish getting movies by id = {}. It took {} ms", id, System.currentTimeMillis() - startTime);
        return movie;
    }

    @Override
    public void add(MovieDtoForUpdate movie) {
        log.info("Start adding movie to db");
        long startTime = System.currentTimeMillis();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movie.getNameRussian());
            statement.setString(2, movie.getNameNative());
            statement.setInt(3, movie.getYearOfRelease());
            statement.setString(4, movie.getDescription());
            statement.setDouble(5, movie.getRating());
            statement.setDouble(6, movie.getPrice());
            statement.setString(7, movie.getPicturePath());
            return statement;
        }, keyHolder);
        movie.setId(keyHolder.getKey().intValue());
        addMovieCountries(movie);
        addMovieGenres(movie);
        log.info("Finish adding movie to db. It took {} ms", System.currentTimeMillis() - startTime);
    }

    private void addMovieCountries(MovieDtoForUpdate movie) {
        log.info("Start adding mappings movieToCountry to db");
        int[] countries = movie.getCountries();
        int movieId = movie.getId();
        List<Object[]> batch = new ArrayList<>();
        for (int countryId : countries) {
            Object[] values = new Object[]{
                    movieId,
                    countryId
            };
            batch.add(values);
        }
        jdbcTemplate.batchUpdate(queryAddMovieCountry, batch);
        log.info("Finish adding mappings movieToCountry to db");
    }

    private void addMovieGenres(MovieDtoForUpdate movie) {
        log.info("Start adding mappings movieToGenre to db");
        int[] genres = movie.getGenres();
        int movieId = movie.getId();
        List<Object[]> batch = new ArrayList<>();
        for (int genreId : genres) {
            Object[] values = new Object[]{
                    movieId,
                    genreId
            };
            batch.add(values);
        }
        jdbcTemplate.batchUpdate(queryAddMovieGenre, batch);
        log.info("Finish adding mappings movieToGenre to db");
    }

    @Override
    public void update(MovieDtoForUpdate movie) {
        int id = movie.getId();
        log.info("Start updating movie with id = {}", id);
        long startTime = System.currentTimeMillis();
        Object[] params = {
                movie.getNameRussian(),
                movie.getNameNative(),
                movie.getYearOfRelease(),
                movie.getDescription(),
                movie.getRating(),
                movie.getPrice(),
                movie.getPicturePath(),
                id
        };
        jdbcTemplate.update(queryUpdate, params);
        jdbcTemplate.update("DELETE FROM movie2country WHERE movieId = ?", id);
        addMovieCountries(movie);
        jdbcTemplate.update("DELETE FROM movie2genre WHERE movieId = ?", id);
        addMovieGenres(movie);
        log.info("Finish updating movie with id = {}. It took {} ms", id, System.currentTimeMillis() - startTime);

    }

    @Override
    public void addRatings(List<MovieRating> movieRatingList) {
        log.info("Start adding ratings to db");
        long startTime = System.currentTimeMillis();
        List<Object[]> batch = new ArrayList<>();

        for (MovieRating movieRating : movieRatingList) {
            Object[] values = new Object[]{
                    movieRating.getMovieId(),
                    movieRating.getUserId(),
                    movieRating.getRating(),
                    movieRating.getRating()
            };
            batch.add(values);
        }

        jdbcTemplate.batchUpdate(queryAddRating, batch);

        log.info("Finish adding ratings to db. It took {} ms", System.currentTimeMillis() - startTime);
    }

    @Override
    public List<MovieRating> getRatings() {
        log.info("Start getting ratings for all movies");
        long startTime = System.currentTimeMillis();

        List<MovieRating> movieRatingList = jdbcTemplate.query(queryGetRatings, movieRatingRowMapper);

        log.info("Finish getting ratings for all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return movieRatingList;
    }

    @Override
    public List<Movie> searchByTitle(String title, int page) {
        log.info("Start search movies by title = '{}' and page = {} in db", title, page);
        long startTime = System.currentTimeMillis();
        String param = "%" + title.toLowerCase().trim() + "%";
        String preparedQuery = querySearchByTitle;

        if (page != 0) {
            preparedQuery = preparedQuery + " ORDER BY id LIMIT " + (page * 5 - 5) + ", 5";
        }

        List<Movie> movieList = jdbcTemplate.query(preparedQuery, movieRowMapper, param, param);

        log.info("Finish search movies by title = '{}' and page = {} in db. It took {} ms", title, page, System.currentTimeMillis() - startTime);
        return movieList;
    }
}


