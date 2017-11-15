package com.lebediev.movieland.web.controller.dto;


import com.lebediev.movieland.entity.User;

public class ReviewDto {
    public int reviewId;
    public User user;
    public String review;

    public ReviewDto(int reviewId, User user, String review) {
        this.reviewId = reviewId;
        this.user = user;
        this.review = review;
    }

    public ReviewDto() {
    }
}
