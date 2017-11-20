package com.lebediev.movieland.web.controller.utils;

import com.lebediev.movieland.service.authentication.UserToken;
import com.lebediev.movieland.web.controller.dto.TokenDto;

public class TokenDtoConverter {

    public static TokenDto toTokenDto(UserToken userToken){
        return new TokenDto(userToken.getUuid(), userToken.getUser().getNickName());
    }
}
