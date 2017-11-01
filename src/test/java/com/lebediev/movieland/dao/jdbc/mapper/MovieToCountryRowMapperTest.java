package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.MovieToCountry;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieToCountryRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(-5).thenReturn(18);
        when(resultSet.getString(any())).thenReturn("testCountryName");

        MovieToCountryRowMapper movieToCountryRowMapper = new MovieToCountryRowMapper();
        MovieToCountry actual = movieToCountryRowMapper.mapRow(resultSet, 0);

        assertEquals(actual.getMovieId(), -5);
        assertEquals(actual.getCountryId(), 18);
        assertEquals(actual.getCountryName(), "testCountryName");


    }
}
