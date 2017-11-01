package com.lebediev.movieland.dao.jdbc.impl;

import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class MovieDAOJdbcIntegrationTest {
    @Autowired
    private MovieDAOJdbc movieDAOJdbc;

    @Autowired
    GenreDAOJdbc genreDAOJdbc;

    @Test
    public void testGetAllMovies() {
        List <Movie> movieList = movieDAOJdbc.getAllMovies();
        assertNotEquals(movieList.size(), 0);
    }

    @Test
    public void getGetRandomMovies(){
        List <Movie> movieList = movieDAOJdbc.getRandomMovies();
        assertEquals(movieList.size(),3);
        assertNotNull(movieList.get(0).getGenres());
    }

}
