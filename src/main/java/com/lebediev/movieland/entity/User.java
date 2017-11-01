package com.lebediev.movieland.entity;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(int userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", userName='" + userName + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
