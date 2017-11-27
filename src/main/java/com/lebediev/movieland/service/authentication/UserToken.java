package com.lebediev.movieland.service.authentication;

import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserToken {
    private final UUID uuid;
    private final LocalDateTime expirationTime;
    private final User user;

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

    public boolean isUser(){
        for(Role role : user.getRoles()){
            if(role == Role.USER){
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(){
        for(Role role : user.getRoles()){
            if(role == Role.ADMIN){
                return true;
            }
        }
        return false;
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
