package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(42).thenReturn(1999);
        when(resultSet.getString(any())).thenReturn("testNameRussian").thenReturn("testNameNative").thenReturn("testDescription").thenReturn("testPicturePath");
        when(resultSet.getDouble(any())).thenReturn(0.1).thenReturn(2.2);

        MovieRowMapper movieRowMapper = new MovieRowMapper();
        Movie actual = movieRowMapper.mapRow(resultSet, 0);

        assertEquals(actual.getId(), 42);
        assertEquals(actual.getNameRussian(), "testNameRussian");
        assertEquals(actual.getNameNative(), "testNameNative");
        assertEquals(actual.getYearOfRelease(), 1999);
        assertEquals(actual.getDescription(), "testDescription");
        assertEquals(actual.getPicturePath(), "testPicturePath");
        assertEquals(actual.getPrice(), 2.2, 0);
        assertEquals(actual.getRating(), 0.1, 0);


    }
}
