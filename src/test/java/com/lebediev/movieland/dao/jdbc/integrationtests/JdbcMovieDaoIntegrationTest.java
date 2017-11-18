package com.lebediev.movieland.dao.jdbc.integrationtests;

import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.JdbcMovieDao;
import com.lebediev.movieland.dao.jdbc.entity.OrderBy;
import com.lebediev.movieland.dao.jdbc.entity.SortDirection;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
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

public class JdbcMovieDaoIntegrationTest {

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


}
