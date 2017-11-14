package com.lebediev.movieland.web.controller.utils;

import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.web.controller.dto.UserDto;

public class UserDtoConverter {
    static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.userId = user.getUserId();
        userDto.userName = user.getUserName();
        return userDto;
    }
}
