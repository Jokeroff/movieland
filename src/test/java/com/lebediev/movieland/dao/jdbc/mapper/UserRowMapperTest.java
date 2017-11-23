package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRowMap() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        String roles = "ADMIN,USER";
        when(resultSet.getInt(any())).thenReturn(18);
        when(resultSet.getString("nickname")).thenReturn("testNickname");
        when(resultSet.getString("email")).thenReturn("testEmail");
        when(resultSet.getString("password")).thenReturn("testPassword");
        when(resultSet.getString("roles")).thenReturn(roles);

        UserRowMapper userRowMapper = new UserRowMapper();
        User actual = userRowMapper.mapRow(resultSet, 0);
        assertEquals(actual.getUserId(), 18);
        assertEquals(actual.getNickName(), "testNickname");
        assertEquals(actual.getEmail(), "testEmail");
        assertEquals(actual.getPassword(), "testPassword");
        assertEquals(actual.getRoles().get(0), Role.ADMIN);
        assertEquals(actual.getRoles().get(1), Role.USER);

        thrown.expect(IllegalArgumentException.class);
        when(resultSet.getString("roles")).thenReturn("");
        userRowMapper.mapRow(resultSet, 0);
    }
}
