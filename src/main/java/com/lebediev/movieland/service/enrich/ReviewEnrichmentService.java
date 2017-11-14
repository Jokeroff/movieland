package com.lebediev.movieland.service.enrich;

import com.lebediev.movieland.dao.jdbc.JdbcUserDao;
import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewEnrichmentService {
    @Autowired
    private JdbcUserDao jdbcUserDao;

    public void enrichReviewByUser(Review review) {
        User user = jdbcUserDao.getUserById(review.getUserId());
        review.setUser(user);
    }

    public void enrichReviewByUser(List <Review> reviewList) {
        for (Review review : reviewList) {
            enrichReviewByUser(review);
        }
    }
}
