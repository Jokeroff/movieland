package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.Review;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(2).thenReturn(4).thenReturn(6);
        when(resultSet.getString(any())).thenReturn("testReview");


        ReviewRowMapper reviewRowMapper = new ReviewRowMapper();
        Review actual = reviewRowMapper.mapRow(resultSet, 0);
        assertEquals(actual.getId(), 2);
        assertEquals(actual.getMovieId(), 4);
        assertEquals(actual.getUserId(), 6);
        assertEquals(actual.getText(), "testReview");
    }
}
