package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRowMapper implements RowMapper <User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        List <Role> roleList = new ArrayList <>();
        String roles = resultSet.getString("roles");
        String[] rolesArray = roles.split(",");

        for (String stringRole : rolesArray) {
            roleList.add(Role.getRole(stringRole));
        }

        return new User(resultSet.getInt("id"),
                        resultSet.getString("nickname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        roleList);
    }
}
