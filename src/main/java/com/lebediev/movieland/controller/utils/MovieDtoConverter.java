package com.lebediev.movieland.controller.utils;

import com.lebediev.movieland.controller.dto.MovieDto;
import com.lebediev.movieland.entity.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieDtoConverter {

    public static List <MovieDto> toMovieDtoList(List <Movie> moviesList) {
        List <MovieDto> movieDtoList = new ArrayList <>();
        for (Movie movie : moviesList) {
            movieDtoList.add(toMovieDto(movie));
        }
        return movieDtoList;
    }

    public static MovieDto toMovieDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.movieId = movie.getMovieId();
        movieDto.movieNameRus = movie.getMovieNameRus();
        movieDto.movieNameNative = movie.getMovieNameNative();
        movieDto.date = movie.getDate();
        movieDto.description = movie.getDescription();
        movieDto.rating = movie.getRating();
        movieDto.price = movie.getPrice();
        movieDto.genres = movie.getGenres();
        movieDto.countries = movie.getCountries();
        movieDto.poster = movie.getPoster();
        return movieDto;
    }
}
