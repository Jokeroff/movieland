package com.lebediev.movieland.dao;

import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAllGenres();

    List<MovieToGenre> getMovieToGenreMappings();
}
