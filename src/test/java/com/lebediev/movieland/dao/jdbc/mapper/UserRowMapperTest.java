package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.User;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void testRowMap() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(18);
        when(resultSet.getString(any())).thenReturn("testUserName").thenReturn("testEmail").thenReturn("testPassword");

        UserRowMapper userRowMapper = new UserRowMapper();
        User actual = userRowMapper.mapRow(resultSet, 0);
        assertEquals(actual.getUserId(), 18);
        assertEquals(actual.getUserName(), "testUserName");
        assertEquals(actual.getEmail(), "testEmail");
        assertEquals(actual.getPassword(), "testPassword");
    }
}
