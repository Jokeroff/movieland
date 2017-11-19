package com.lebediev.movieland.entity;

public class User {
    private int userId;
    private String nickname;
    private String email;
    private String password;

    public User() {
    }

    public User(int userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
