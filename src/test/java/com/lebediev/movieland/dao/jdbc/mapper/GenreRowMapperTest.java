package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.Genre;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(33);
        when(resultSet.getString(any())).thenReturn("testGenre");

        GenreRowMapper genreRowMapper = new GenreRowMapper();
        Genre actual = genreRowMapper.mapRow(resultSet, 0);
        assertEquals(actual.getGenreId(), 33);
        assertEquals(actual.getGenreName(), "testGenre");

    }
}
