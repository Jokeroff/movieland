package com.lebediev.movieland.dao;

import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.MovieToGenre;

import java.util.List;

public interface GenreDAO {
    List<MovieToGenre> getMovieToGenreMappings();
    List<Genre> getGenresByMovieId(List<MovieToGenre> movieToGenreList, int movieId);
}
