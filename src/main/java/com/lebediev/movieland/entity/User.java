package com.lebediev.movieland.entity;

public class User {
    private int userId;
    private String userName;

    public User() {
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", userName='" + userName + '\'' +
               '}';
    }
}
