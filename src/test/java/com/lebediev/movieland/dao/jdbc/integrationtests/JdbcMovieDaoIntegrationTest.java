package com.lebediev.movieland.dao.jdbc.integrationtests;

import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.JdbcMovieDao;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testGetAllMovies() {
        List<Movie> movieList = jdbcMovieDao.getAllMovies();
        assertNotEquals(movieList.size(), 0);
        System.out.println(movieList);
    }

    @Test
    public void testGetRandomMovies() {
        List<Movie> movieList = jdbcMovieDao.getRandomMovies();
        assertEquals(movieList.size(), 3);
        assertNotNull(movieList.get(0).getGenres());
        assertNotNull(movieList.get(1).getCountries());
    }

    @Test
    public void testGetMoviesByGenreId() {
        List<Genre> genreList = jdbcGenreDao.getAllGenres();
        assertNotEquals(genreList.size(), 0);
        List<Movie> movieList = jdbcMovieDao.getMoviesByGenreId(genreList.get(0).getGenreId());
        assertNotEquals(movieList.size(), 0);
    }

    @Test
    public void testGetAllMoviesOrdered() {
        Map<String, String> params = new HashMap<>();
        params.put("price", "desc");
        List<Movie> movieList = jdbcMovieDao.getAllMovies(params);
        assertNotEquals(movieList.size(), 0);
        Double maxPrice = jdbcTemplate.queryForObject("select max(price) from movie", Double.class);
        assertEquals(movieList.get(0).getPrice(), maxPrice, 0);

        params.clear();
        params.put("rating", "asc");
        movieList = jdbcMovieDao.getAllMovies(params);
        assertNotEquals(movieList.size(), 0);
        Double minRating = jdbcTemplate.queryForObject("select min(rating) from movie", Double.class);
        assertEquals(movieList.get(0).getRating(), minRating, 0);

        params.clear();
        params.put(null, null);
        movieList = jdbcMovieDao.getAllMovies(params);
        assertNotEquals(movieList.size(), 0);

    }

    @Test
    public void testGetMoviesByGenreIdOrdered() {
        Map<String, String> params = new HashMap<>();

        int genreId = jdbcTemplate.queryForObject("select avg(genreId) from genre", int.class);
        List<Movie> movieList = jdbcMovieDao.getMoviesByGenreId(genreId, params);
        assertNotEquals(movieList.size(), 0);
        params.put("price", "desc");
        movieList = jdbcMovieDao.getMoviesByGenreId(genreId, params);
        assertNotEquals(movieList.size(), 0);
        params.clear();
        params.put("rating", "desc");
        movieList = jdbcMovieDao.getMoviesByGenreId(genreId, params);
        assertNotEquals(movieList.size(), 0);
    }


}
