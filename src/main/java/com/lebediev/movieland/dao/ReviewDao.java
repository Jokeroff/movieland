package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {
    List <Review> getReviewById(int id);
    Review add(Review review);
}
