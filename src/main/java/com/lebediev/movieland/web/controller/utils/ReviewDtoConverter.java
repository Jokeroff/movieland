package com.lebediev.movieland.web.controller.utils;

import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.web.controller.dto.ReviewDto;

import java.util.ArrayList;
import java.util.List;

import static com.lebediev.movieland.web.controller.utils.UserDtoConverter.toUserDto;

public class ReviewDtoConverter {
    static ReviewDto toReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.reviewId = review.getReviewId();
        reviewDto.review = review.getReview();
        reviewDto.user = toUserDto(review.getUser());
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
