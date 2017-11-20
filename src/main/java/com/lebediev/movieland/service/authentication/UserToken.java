package com.lebediev.movieland.service.authentication;

import com.lebediev.movieland.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserToken {
    private UUID uuid;
    private LocalDateTime expirationTime;
    private User user;

    public UserToken(UUID uuid, LocalDateTime expirationTime, User user) {
        this.uuid = uuid;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "uuid=" + uuid +
                ", expirationTime=" + expirationTime +
                ", user=" + user +
                '}';
    }
}
