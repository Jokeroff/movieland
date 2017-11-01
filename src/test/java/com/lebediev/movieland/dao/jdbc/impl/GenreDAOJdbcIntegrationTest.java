package com.lebediev.movieland.dao.jdbc.impl;


import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.MovieToGenre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class GenreDAOJdbcIntegrationTest {

    @Autowired
    GenreDAOJdbc genreDAOJdbc;

    @Test
    public void getMovieToGenreMappings(){
        List<MovieToGenre> movieToGenreList = genreDAOJdbc.getMovieToGenreMappings();
        assertNotEquals(movieToGenreList.size(), 0);
    }

    @Test
    public void testGetGenresByMovieId(){
        List<MovieToGenre> movieToGenreList = genreDAOJdbc.getMovieToGenreMappings();
        List<Genre> genreList = genreDAOJdbc.getGenresByMovieId(movieToGenreList, movieToGenreList.get(0).getMovieId());
        assertNotEquals(genreList, 0);
    }

}
