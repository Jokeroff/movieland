package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper <User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        return new User(resultSet.getInt("userId"),
                        resultSet.getString("userName"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));

    }
}
