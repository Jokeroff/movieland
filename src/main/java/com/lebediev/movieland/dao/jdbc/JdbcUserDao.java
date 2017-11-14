package com.lebediev.movieland.dao.jdbc;

import com.lebediev.movieland.dao.UserDao;
import com.lebediev.movieland.dao.jdbc.mapper.UserRowMapper;
import com.lebediev.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements UserDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${query.getUserById}")
    private String queryGetUserById;

    @Override
    public User getUserById(int userId) {
        log.info("Start getting user by id = {}", userId);
        long startTime = System.currentTimeMillis();
        User user = jdbcTemplate.queryForObject(queryGetUserById, userRowMapper, userId);
        log.info("Finish getting user by id = {}. It took {} ms", userId, System.currentTimeMillis() - startTime);
        return user;
    }
}
