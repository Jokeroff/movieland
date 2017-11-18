package com.lebediev.movieland.entity;

public class Review {
    private int id;
    private int movieId;
    private int userId;
    private User user;
    private String text;

    public Review() {
    }

    public int getId() {
        return id;
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

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review{" +
               "id=" + id +
               ", movieId=" + movieId +
               ", userId=" + userId +
               ", user=" + user +
               ", text='" + text + '\'' +
               '}';
    }
}
