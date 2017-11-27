package com.lebediev.movieland.web.controller.dto;

import java.util.UUID;

public class TokenDto {
    private final UUID uuid;
    private String nickname;

    public TokenDto(UUID uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }
}
