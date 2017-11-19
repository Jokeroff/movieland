package com.lebediev.movieland.service.security;

import com.lebediev.movieland.dao.UserDao;
import com.lebediev.movieland.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;


public class SecurityService {

    @Autowired
    UserDao userDao;

    public User checkPassword(String email, String password){
        User user = userDao.getUserByEmail(email);
        if(BCrypt.checkpw(password, user.getPassword() )){
            return user;
        }
        throw new IllegalArgumentException("Incorrect password for email: " + email);
    }

 }
