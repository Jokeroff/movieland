package com.lebediev.movieland.dao.jdbc.integrationtests;

import com.lebediev.movieland.dao.jdbc.JdbcMovieDao;
import com.lebediev.movieland.dao.jdbc.JdbcReviewDao;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class JdbcReviewDaoIntegrationTest {

    @Autowired
    JdbcReviewDao jdbcReviewDao;
    @Autowired
    JdbcMovieDao jdbcMovieDao;

    @Test
    public void testGetReviewByMovieId() {
        List <Movie> movieList = jdbcMovieDao.getAll(new SortParams());
        assertNotEquals(movieList.size(), 0);
        int movieId = movieList.get(0).getMovieId();
        List <Review> actual = jdbcReviewDao.getReviewByMovieId(movieId);
        assertNotEquals(actual.size(), 0);
    }
}
