package com.lebediev.movieland.web.controller.utils;

import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.web.controller.dto.MovieDto;

import java.util.ArrayList;
import java.util.List;

import static com.lebediev.movieland.web.controller.utils.ReviewDtoConverter.toReviewDtoList;


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
        movieDto.id = movie.getId();
        movieDto.nameRussian = movie.getNameRussian();
        movieDto.nameNative = movie.getNameNative();
        movieDto.yearOfRelease = movie.getYearOfRelease();
        movieDto.description = movie.getDescription();
        movieDto.rating = movie.getRating();
        movieDto.price = movie.getPrice();
        movieDto.genres = movie.getGenres();
        movieDto.countries = movie.getCountries();
        movieDto.picturePath = movie.getPicturePath();
        if (movie.getReviews() != null) {
            movieDto.reviews = toReviewDtoList(movie.getReviews());
        }
        return movieDto;
    }
}
