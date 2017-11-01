package com.lebediev.movieland.controller.dto.movie;

import com.lebediev.movieland.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieDTOConverter {

    public static List <MovieDTO> toMovieDTOList(List <Movie> moviesList) {
        List <MovieDTO> movieDTOList = new ArrayList <>();
        for (Movie movie : moviesList) {
            movieDTOList.add(toMovieDTO(movie));
        }
        return movieDTOList;
    }

    public static MovieDTO toMovieDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.movieId = movie.getMovieId();
        movieDTO.movieNameRus = movie.getMovieNameRus();
        movieDTO.movieNameNative = movie.getMovieNameNative();
        movieDTO.date = movie.getDate();
        movieDTO.description = movie.getDescription();
        movieDTO.rating = movie.getRating();
        movieDTO.price = movie.getPrice();
        movieDTO.genres = movie.getGenres();
        movieDTO.countries = movie.getCountries();
        movieDTO.poster = movie.getPoster();
        return movieDTO;
    }
}
