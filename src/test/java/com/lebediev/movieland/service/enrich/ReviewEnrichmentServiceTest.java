package com.lebediev.movieland.dao.jdbc.enrich;

import com.lebediev.movieland.dao.jdbc.JdbcUserDao;
import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.service.enrich.ReviewEnrichmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

public class ReviewEnrichmentServiceTest {

    @Spy
    private JdbcUserDao jdbcUserDao;
    @InjectMocks
    private ReviewEnrichmentService reviewEnrichmentService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        doReturn(user).when(jdbcUserDao).getUserById(anyInt());
    }

    @Test
    public void testEnrichReviewByUser() {
        Review actual = new Review();
        assertEquals(actual.getUser(), null);
        reviewEnrichmentService.enrichReviewByUser(actual);
        assertNotEquals(actual.getUser(), null);
    }

    @Test
    public void testEnrichReviewsByUser() {
        Review reviewOne = new Review();
        Review reviewTwo = new Review();
        List <Review> actual = new ArrayList <>();
        actual.add(reviewOne);
        actual.add(reviewTwo);
        assertEquals(actual.get(0).getUser(), null);
        assertEquals(actual.get(1).getUser(), null);
        reviewEnrichmentService.enrichReviewByUser(actual);
        assertNotEquals(actual.get(0).getUser(), null);
        assertNotEquals(actual.get(1).getUser(), null);
    }
}
