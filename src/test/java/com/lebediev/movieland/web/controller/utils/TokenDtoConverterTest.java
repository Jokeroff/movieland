package com.lebediev.movieland.web.controller.utils;

import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.service.authentication.UserToken;
import com.lebediev.movieland.web.controller.dto.TokenDto;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.lebediev.movieland.web.controller.utils.TokenDtoConverter.toTokenDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TokenDtoConverterTest {

    @Test
    public void testToTokenDto(){
        User user = new User(18,"nickname","email","password");
        UserToken userToken = new UserToken(UUID.randomUUID(),
                LocalDateTime.now(),
                user);
        TokenDto expected = toTokenDto(userToken);
        assertNotEquals(expected.getUuid(), null);
        assertEquals(expected.getNickname(), "nickname");

    }
}
