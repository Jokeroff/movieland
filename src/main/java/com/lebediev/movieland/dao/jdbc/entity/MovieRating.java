package com.lebediev.movieland.dao.jdbc.entity;

public class MovieRating {
    private int movieId;
    private int userId;
    private double rating;
    private int voteCount;

    public MovieRating() {
    }

    public MovieRating(int movieId, int voteCount, double rating) {
        this.movieId = movieId;
        this.voteCount = voteCount;
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieRating that = (MovieRating) o;

        return movieId == that.movieId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = movieId;
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "MovieRating{" +
                "movieId=" + movieId +
                ", userId=" + userId +
                ", rating=" + rating +
                ", voteCount=" + voteCount +
                '}';
    }
}
