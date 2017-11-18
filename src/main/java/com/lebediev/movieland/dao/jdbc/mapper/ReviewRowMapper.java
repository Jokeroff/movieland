package com.lebediev.movieland.dao.jdbc.mapper;


import com.lebediev.movieland.entity.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper <Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getInt("id"));
        review.setText(resultSet.getString("text"));
        review.setMovieId(resultSet.getInt("movieId"));
        review.setUserId(resultSet.getInt("userId"));

        return review;
    }
}
