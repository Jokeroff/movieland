package com.lebediev.movieland.entity;

public class Review {
    private int reviewId;
    private int movieId;
    private int userId;
    private String review;

    public Review(int reviewId, int movieId, int userId, String review) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.userId = userId;
        this.review = review;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getUserId() {
        return userId;
    }

    public String getReview() {
        return review;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", review='" + review + '\'' +
                '}';
    }
}
