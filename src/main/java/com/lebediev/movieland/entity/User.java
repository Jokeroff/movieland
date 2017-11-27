package com.lebediev.movieland.entity;

import com.lebediev.movieland.dao.jdbc.entity.Role;

import java.util.List;

public class User {
    private int userId;
    private String nickname;
    private String email;
    private String password;
    private List<Role> roles;

    public User() {
    }

    public User(int userId, String nickname, String email, String password, List <Role> roles) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public List <Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", nickname='" + nickname + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", roles=" + roles +
               '}';
    }
}
