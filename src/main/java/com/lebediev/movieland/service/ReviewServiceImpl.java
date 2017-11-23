package com.lebediev.movieland.service;

import com.lebediev.movieland.dao.ReviewDao;
import com.lebediev.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;
    @Override
    public void add(Review review) {
        reviewDao.add(review);
    }
}
