package com.lebediev.movieland.entity;

public class Review {
    private int reviewId;
    private int movieId;
    private int userId;
    private User user;
    private String review;

    public Review() {
    }

    public Review(int reviewId, int movieId, int userId, User user, String review) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.userId = userId;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public String getReview() {
        return review;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Review{" +
               "reviewId=" + reviewId +
               ", movieId=" + movieId +
               ", userId=" + userId +
               ", user=" + user +
               ", review='" + review + '\'' +
               '}';
    }
}
