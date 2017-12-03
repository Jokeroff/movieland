package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRatingRowMapper implements RowMapper<MovieRating> {
    @Override
    public MovieRating mapRow(ResultSet resultSet, int i) throws SQLException {
        return new MovieRating(resultSet.getInt("movieId"),
                resultSet.getInt("voteCount"),
                resultSet.getDouble("rating"));
    }
}
