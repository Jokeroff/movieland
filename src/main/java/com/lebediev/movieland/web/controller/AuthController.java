package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.service.authentication.AuthRequest;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.web.controller.dto.TokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.getAuthFromJson;
import static com.lebediev.movieland.web.controller.utils.TokenDtoConverter.toTokenDto;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {
    private final static Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TokenDto login(@RequestBody String value) {
        LOG.info("Start getting json for /login with request body: {}", value);
        AuthRequest authRequest = getAuthFromJson(value);
        LOG.info("Finish getting json for /login with request body: {}", value);
        return toTokenDto(authService.authenticate(authRequest.getEmail(), authRequest.getPassword()));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    @ResponseBody
    public void logout(@RequestHeader String uuid) {
        LOG.info("Start deleting token for /logout with uuid: {}", uuid);
        authService.deleteToken(UUID.fromString(uuid));
        LOG.info("Finish deleting token for /logout with uuid: {}", uuid);
    }

}
