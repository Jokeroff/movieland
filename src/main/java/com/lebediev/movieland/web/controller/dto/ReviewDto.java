package com.lebediev.movieland.web.controller.dto;


import com.lebediev.movieland.entity.User;

public class ReviewDto {
    public int id;
    public User user;
    public String text;

    public ReviewDto(int id, User user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }

    public ReviewDto() {
    }
}
