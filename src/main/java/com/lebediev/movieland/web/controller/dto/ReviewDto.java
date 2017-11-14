package com.lebediev.movieland.web.controller.dto;


public class ReviewDto {
    public int reviewId;
    public UserDto user;
    public String review;

    public ReviewDto(int reviewId, UserDto userDto, String review) {
        this.reviewId = reviewId;
        this.user = userDto;
        this.review = review;
    }

    public ReviewDto() {
    }
}
