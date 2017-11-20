package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.service.authentication.AuthRequest;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.web.controller.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.getAuthFromJson;
import static com.lebediev.movieland.web.controller.utils.TokenDtoConverter.toTokenDto;

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {
    @Autowired
    AuthService authService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TokenDto login(@RequestBody String value) {
        AuthRequest authRequest = getAuthFromJson(value);
        return toTokenDto(authService.getToken(authRequest.getEmail(), authRequest.getPassword()));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    @ResponseBody
    public void logout(@RequestHeader String uuid) {
        authService.deleteToken(UUID.fromString(uuid));
    }

}
