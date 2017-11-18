package com.lebediev.movieland.web.controller.utils;

import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDto;
import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDtoList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieDtoConverterTest {

    @Test
    public void testToMovieDto() {
        Movie movie = mock(Movie.class);

        when(movie.getId()).thenReturn(44);
        when(movie.getNameRussian()).thenReturn("testNameRussian");
        when(movie.getNameNative()).thenReturn("testNameNative");
        when(movie.getYearOfRelease()).thenReturn(1999);
        when(movie.getDescription()).thenReturn("testDescription");
        when(movie.getPicturePath()).thenReturn("testPicturePath");
        when(movie.getPrice()).thenReturn(2.2);
        when(movie.getRating()).thenReturn(0.1);
        Genre genre = mock(Genre.class);
        when(genre.getId()).thenReturn(330);
        when(movie.getGenres()).thenReturn(Arrays.asList(genre));
        Country country = mock(Country.class);
        when(country.getName()).thenReturn("testCountryName");
        when(movie.getCountries()).thenReturn(Arrays.asList(country));

        MovieDto actual = toMovieDto(movie);
        assertEquals(actual.id, 44);
        assertEquals(actual.nameRussian, "testNameRussian");
        assertEquals(actual.nameNative, "testNameNative");
        assertEquals(actual.yearOfRelease, 1999);
        assertEquals(actual.description, "testDescription");
        assertEquals(actual.picturePath, "testPicturePath");
        assertEquals(actual.price, 2.2, 0);
        assertEquals(actual.rating, 0.1, 0);
        assertEquals(actual.genres.get(0).getId(), 330);
        assertEquals(actual.countries.get(0).getName(), "testCountryName");
    }

    @Test
    public void testToMovieDtoList() {
        List <Movie> moviesList = new ArrayList <>();
        Movie movieOne = mock(Movie.class);
        Movie movieTwo = mock(Movie.class);
        when(movieOne.getId()).thenReturn(222);
        when(movieTwo.getId()).thenReturn(333);
        moviesList.add(movieOne);
        moviesList.add(movieTwo);

        List <MovieDto> actual = toMovieDtoList(moviesList);
        assertEquals(actual.get(0).id, 222);
        assertEquals(actual.get(1).id, 333);
    }

}
