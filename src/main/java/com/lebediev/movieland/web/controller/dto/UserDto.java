package com.lebediev.movieland.web.controller.dto;

public class UserDto {
    public int userId;
    public String userName;

    public UserDto(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public UserDto() {
    }
}
