package com.lebediev.movieland.dao.jdbc.integrationtests;


import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class JdbcGenreDaoIntegrationTest {

    @Autowired
    JdbcGenreDao jdbcGenreDao;

    @Test
    public void testGetMovieToGenreMappings(){
        List<MovieToGenre> movieToGenreList = jdbcGenreDao.getMovieToGenreMappings();
        assertNotEquals(movieToGenreList.size(), 0);
    }

    @Test
    public void testGetAllGenres(){
        List<Genre> genreList = jdbcGenreDao.getAllGenres();
        assertNotEquals(genreList.size(),0);
    }

}
