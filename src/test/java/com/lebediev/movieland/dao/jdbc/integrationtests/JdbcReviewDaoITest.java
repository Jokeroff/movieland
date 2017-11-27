package com.lebediev.movieland.dao.jdbc.integrationtests;

import com.lebediev.movieland.dao.jdbc.JdbcMovieDao;
import com.lebediev.movieland.dao.jdbc.JdbcReviewDao;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class JdbcReviewDaoITest {

    @Autowired
    private JdbcReviewDao jdbcReviewDao;
    @Autowired
    private JdbcMovieDao jdbcMovieDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testGetReviewById() {
        List <Movie> movieList = jdbcMovieDao.getAll(new SortParams());
        assertNotEquals(movieList.size(), 0);
        int id = movieList.get(0).getId();
        List <Review> actual = jdbcReviewDao.getReviewById(id);
        assertNotEquals(actual.size(), 0);
    }

    @Test
    public void testAddReview(){
        Review review = new Review();
        review.setUserId(-1);
        review.setMovieId(-1);
        review.setText("some text");

        int countBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM review WHERE movieId = -1", Integer.class);
        assertEquals(0, countBefore);
        Review addedReview = jdbcReviewDao.add(review);
        assertNotNull(addedReview.getId());
        int countAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM review WHERE movieId = -1", Integer.class);
        assertEquals(1, countAfter);
        jdbcTemplate.update("DELETE FROM review WHERE movieId = -1");
    }
}
