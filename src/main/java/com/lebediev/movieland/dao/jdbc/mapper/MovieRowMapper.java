package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MovieRowMapper implements RowMapper <Movie> {

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        int movieId = resultSet.getInt("movieId");
        movie.setMovieId(movieId);
        movie.setMovieNameRus(resultSet.getString("movieNameRus"));
        movie.setMovieNameNative(resultSet.getString("movieNameNative"));
        movie.setDate(resultSet.getInt("date"));
        movie.setDescription(resultSet.getString("description"));
        movie.setRating(resultSet.getDouble("rating"));
        movie.setPrice(resultSet.getDouble("price"));
        movie.setPoster(resultSet.getString("poster"));
        return movie;
    }
}
