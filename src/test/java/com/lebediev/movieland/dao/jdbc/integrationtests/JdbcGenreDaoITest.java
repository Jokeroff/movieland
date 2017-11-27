package com.lebediev.movieland.dao.jdbc.integrationtests;


import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.JdbcMovieDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class JdbcGenreDaoITest {

    @Autowired
    private JdbcGenreDao jdbcGenreDao;
    @Autowired
    private JdbcMovieDao jdbcMovieDao;


    @Test
    public void testGetMovieToGenreMappings() {
        List <Movie> movieList = jdbcMovieDao.getAll(new SortParams());
        List <Integer> params = Arrays.asList(movieList.get(0).getId(),
                                              movieList.get(1).getId(),
                                              movieList.get(2).getId());
        List <MovieToGenre> movieToGenreList = jdbcGenreDao.getMovieToGenreMappings(params);
        assertNotEquals(movieToGenreList.size(), 0);
    }

    @Test
    public void testGetAllGenres() {
        List <Genre> genreList = jdbcGenreDao.getAll();
        assertNotEquals(genreList.size(), 0);
    }

}
