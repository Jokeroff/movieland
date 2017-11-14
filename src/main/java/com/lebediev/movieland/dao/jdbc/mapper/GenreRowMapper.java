package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper <Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Genre(resultSet.getInt("genreId"), resultSet.getString("genreName"));
    }
}
