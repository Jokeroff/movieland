package com.lebediev.movieland.web.controller.utils;

import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.web.controller.dto.ReviewDto;

import java.util.ArrayList;
import java.util.List;


public class ReviewDtoConverter {
    static ReviewDto toReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.id = review.getId();
        reviewDto.text = review.getText();
        reviewDto.user = review.getUser();
        return reviewDto;
    }

    static List <ReviewDto> toReviewDtoList(List <Review> reviewList) {
        List <ReviewDto> reviewDtoList = new ArrayList <>();
        for (Review review : reviewList) {
            reviewDtoList.add(toReviewDto(review));
        }
        return reviewDtoList;
    }
}
