package com.lebediev.movieland.dao.jdbc.mapper;

import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieToGenreRowMapper implements RowMapper<MovieToGenre> {

    @Override
    public MovieToGenre mapRow(ResultSet resultSet, int i) throws SQLException {
        MovieToGenre movieToGenre = new MovieToGenre();
        movieToGenre.setMovieId(resultSet.getInt("movieId"));
        movieToGenre.setGenreId(resultSet.getInt("genreId"));
        movieToGenre.setGenreName(resultSet.getString("genreName"));
        return movieToGenre;
    }
}
