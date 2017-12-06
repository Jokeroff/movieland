package com.lebediev.movieland.dao.jdbc.integrationtests;

import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.JdbcMovieDao;
import com.lebediev.movieland.dao.jdbc.entity.OrderBy;
import com.lebediev.movieland.dao.jdbc.entity.SortDirection;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})

public class JdbcMovieDaoITest {

    @Autowired
    private JdbcMovieDao jdbcMovieDao;
    @Autowired
    private JdbcGenreDao jdbcGenreDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testGetRandomMovies() {
        List <Movie> movieList = jdbcMovieDao.getRandomMovies();
        assertEquals(movieList.size(), 3);
        assertNotNull(movieList.get(0).getGenres());
        assertNotNull(movieList.get(1).getCountries());
    }

    @Test
    public void testGetAllMoviesOrdered() {
        SortParams params = new SortParams(OrderBy.PRICE, SortDirection.ASC);
        List <Movie> movieList = jdbcMovieDao.getAll(params);
        assertNotEquals(movieList.size(), 0);
        Double maxPrice = jdbcTemplate.queryForObject("select min(price) from movie", Double.class);
        assertEquals(movieList.get(0).getPrice(), maxPrice, 0);


        params = new SortParams(OrderBy.RATING, SortDirection.DESC);
        movieList = jdbcMovieDao.getAll(params);
        assertNotEquals(movieList.size(), 0);
        Double minRating = jdbcTemplate.queryForObject("select max(rating) from movie", Double.class);
        assertEquals(movieList.get(0).getRating(), minRating, 0);

        params = new SortParams();
        movieList = jdbcMovieDao.getAll(params);
        assertNotEquals(movieList.size(), 0);
    }

    @Test
    public void testGetMoviesByGenreIdOrdered() {
        SortParams params = new SortParams();

        int genreId = jdbcTemplate.queryForObject("select avg(id) from genre", int.class);
        List <Movie> movieList = jdbcMovieDao.getMoviesByGenreId(genreId, params);
        assertNotEquals(movieList.size(), 0);

        params = new SortParams(OrderBy.PRICE, SortDirection.DESC);
        movieList = jdbcMovieDao.getMoviesByGenreId(genreId, params);
        assertNotEquals(movieList.size(), 0);

        params = new SortParams(OrderBy.RATING, SortDirection.DESC);
        movieList = jdbcMovieDao.getMoviesByGenreId(genreId, params);
        assertNotEquals(movieList.size(), 0);
    }

    @Test
    public void testGetMovieById() {
        List <Movie> movieList = jdbcMovieDao.getAll(new SortParams());
        assertNotEquals(movieList.size(), 0);
        int id = movieList.get(0).getId();
        Movie actual = jdbcMovieDao.getMovieById(id);
        assertNotEquals(actual, 0);
    }

    @Test
    public void testAdd() {
        MovieDtoForUpdate movie = new MovieDtoForUpdate();
        movie.setNameNative("ITest movie");
        movie.setYearOfRelease(1900);
        movie.setCountries(new int[]{1, 2, 3});
        movie.setGenres(new int[]{4, 5});
        jdbcMovieDao.add(movie);
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie WHERE nameNative = 'ITest movie' AND yearOfRelease = 1900", Integer.class);
        assertEquals(result, 1);
        int id = jdbcTemplate.queryForObject("SELECT id FROM movie WHERE nameNative = 'ITest movie' AND yearOfRelease = 1900", Integer.class);
        result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie2country WHERE movieId = " + id, Integer.class);
        assertEquals(result, 3);
        result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie2genre WHERE movieId = " + id, Integer.class);
        assertEquals(result, 2);
        jdbcTemplate.update("DELETE FROM movie WHERE nameNative = 'ITest movie' AND yearOfRelease = 1900");
        jdbcTemplate.update("DELETE FROM movie2country WHERE movieId = " + id);
        jdbcTemplate.update("DELETE FROM movie2genre WHERE movieId = " + id);
    }

    @Test
    public void testUpdate() {
        MovieDtoForUpdate actual = new MovieDtoForUpdate();
        actual.setNameNative("before update");
        actual.setYearOfRelease(2000);
        actual.setCountries(new int[]{1, 2, 3});
        actual.setGenres(new int[]{4, 5});
        jdbcMovieDao.add(actual);
        int id = jdbcTemplate.queryForObject("SELECT id FROM movie WHERE nameNative = 'before update' AND yearOfRelease = 2000", Integer.class);

        MovieDtoForUpdate expected = new MovieDtoForUpdate();
        expected.setId(id);
        expected.setNameNative("after update");
        expected.setNameRussian("название");
        expected.setYearOfRelease(1900);
        expected.setDescription("desc");
        expected.setPrice(100);
        expected.setRating(10);
        expected.setCountries(new int[]{1, 2, 3});
        expected.setGenres(new int[]{4, 5});
        expected.setPicturePath("path");

        jdbcMovieDao.update(expected);
        int oldMovie = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie WHERE nameNative = 'before update' AND yearOfRelease = 2000", Integer.class);
        assertEquals(oldMovie, 0);
        int updatedMovie = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie WHERE nameNative = 'after update' AND yearOfRelease = 1900", Integer.class);
        assertEquals(updatedMovie, 1);
        jdbcTemplate.update("DELETE FROM movie WHERE nameNative = 'after update' AND yearOfRelease = 1900");
    }

    @Test
    public void testSearch(){
        List<Movie> movieList = jdbcMovieDao.searchByTitle("миля");
        assertEquals(movieList.size(), 1);
        movieList = jdbcMovieDao.searchByTitle("mile");
        assertEquals(movieList.size(), 1);
    }

}
