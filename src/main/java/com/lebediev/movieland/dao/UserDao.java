package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.User;

public interface UserDao {
    User getById(int userId);
    User getByEmail(String email);
}
