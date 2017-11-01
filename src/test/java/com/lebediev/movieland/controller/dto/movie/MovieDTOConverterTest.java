package com.lebediev.movieland.controller.dto.movie;

import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieDTOConverterTest {

    @Test
    public void testToMovieDTO() {
        Movie movie = mock(Movie.class);

        when(movie.getMovieId()).thenReturn(44);
        when(movie.getMovieNameRus()).thenReturn("testMovieNameRus");
        when(movie.getMovieNameNative()).thenReturn("testMovieNameNative");
        when(movie.getDate()).thenReturn(1999);
        when(movie.getDescription()).thenReturn("testDescription");
        when(movie.getPoster()).thenReturn("testPoster");
        when(movie.getPrice()).thenReturn(2.2);
        when(movie.getRating()).thenReturn(0.1);
        Genre genre = mock(Genre.class);
        when(genre.getGenreId()).thenReturn(330);
        when(movie.getGenres()).thenReturn(Arrays.asList(genre));
        Country country = mock(Country.class);
        when(country.getCountryName()).thenReturn("testCountryName");
        when(movie.getCountries()).thenReturn(Arrays.asList(country));

        MovieDTOConverter movieDTOConverter = new MovieDTOConverter();
        MovieDTO actual = movieDTOConverter.toMovieDTO(movie);
        assertEquals(actual.movieId, 44);
        assertEquals(actual.movieNameRus, "testMovieNameRus");
        assertEquals(actual.movieNameNative, "testMovieNameNative");
        assertEquals(actual.date, 1999);
        assertEquals(actual.description, "testDescription");
        assertEquals(actual.poster, "testPoster");
        assertEquals(actual.price, 2.2, 0);
        assertEquals(actual.rating, 0.1, 0);
        assertEquals(actual.genres.get(0).getGenreId(), 330);
        assertEquals(actual.countries.get(0).getCountryName(), "testCountryName");
    }

    @Test
    public void testToMovieDTOList() {
        List <Movie> moviesList = new ArrayList <>();
        Movie movieOne = mock(Movie.class);
        Movie movieTwo = mock(Movie.class);
        when(movieOne.getMovieId()).thenReturn(222);
        when(movieTwo.getMovieId()).thenReturn(333);
        moviesList.add(movieOne);
        moviesList.add(movieTwo);

        MovieDTOConverter movieDTOConverter = new MovieDTOConverter();
        List <MovieDTO> actual = movieDTOConverter.toMovieDTOList(moviesList);
        assertEquals(actual.get(0).movieId, 222);
        assertEquals(actual.get(1).movieId, 333);
    }

}
