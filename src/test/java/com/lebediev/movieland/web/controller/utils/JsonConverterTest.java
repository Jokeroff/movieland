package com.lebediev.movieland.web.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.service.authentication.AuthRequest;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;
import com.lebediev.movieland.web.controller.dto.ReviewDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonConverterTest {
    private final List <MovieDto> MovieDtoList = new ArrayList <>();
    private List <Genre> genreList;

    @Test
    public void testToJsonMovie() throws JsonProcessingException {

        Country country = new Country(2, "someCountry");
        MovieDto movieDto = new MovieDto(44, "testnameRussian", "testnameNative",
                                         1999, "testDescription", 0.1, 2.2, genreList,
                                         Arrays.asList(country), "testpicturePath", Arrays.asList(new ReviewDto()));
        MovieDtoList.add(movieDto);

        String actual = toJson(MovieDtoList, JsonConverter.JsonView.BASE);
        String expected = "[{\"id\":44,\"nameRussian\":\"testnameRussian\",\"nameNative\":\"testnameNative\"," +
                          "\"yearOfRelease\":1999,\"rating\":0.1,\"price\":2.2,\"picturePath\":\"testpicturePath\"}]";
        assertEquals(expected, actual);

        expected = "[{\"id\":44,\"nameRussian\":\"testnameRussian\",\"nameNative\":\"testnameNative\"," +
                   "\"yearOfRelease\":1999,\"description\":\"testDescription\",\"rating\":0.1,\"price\":2.2," +
                   "\"countries\":[{\"id\":2,\"name\":\"someCountry\"}],\"genres\":[{\"id\":1," +
                   "\"name\":\"криминал\"},{\"id\":2,\"name\":\"drama\"}],\"picturePath\":\"testpicturePath\"}]";
        actual = toJson(MovieDtoList, JsonConverter.JsonView.EXTENDED);
        assertEquals(expected, actual);
    }

    @Before
    public void setup() {
        Genre genre = new Genre(1, "криминал");
        Genre genreTwo = new Genre(2, "drama");
        genreList = Arrays.asList(genre, genreTwo);
    }

    @Test
    public void testToJsonGenre() {
        String actual = toJson(genreList);
        String expected = "[{\"id\":1,\"name\":\"криминал\"},{\"id\":2,\"name\":\"drama\"}]";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAuthFromJson() {
        String value = "{ \"email\" : \"ronald.reynolds66@example.com\",\"password\" : \"paco\" }";
        AuthRequest expected = toAuth(value);
        assertEquals(expected.getEmail(), "ronald.reynolds66@example.com");
        assertEquals(expected.getPassword(), "paco");


    }

    @Test
    public void testToReview() {
        String json = "{ \"movieId\" : 1,\"text\" : \"some review\" }";
        Review expected = toReview(json);
        assertEquals(expected.getMovieId(), 1);
        assertEquals(expected.getText(), "some review");
    }

    @Test
    public void testToMovie() {
        String json = "{ \"nameRussian\" : \"название\", \"nameNative\" : \"testName\", \"yearOfRelease\" : 2000," +
                      "\"description\" : \"some description\"," +
                      "\"rating\" : 10.0, \"price\" : 330,\"picturePath\" : \"http:somepath\" }";
        Movie actual = toMovie(json);
        assertEquals(actual.getNameRussian(), "название");
        assertEquals(actual.getNameNative(), "testName");
        assertEquals(actual.getYearOfRelease(), 2000);
        assertEquals(actual.getDescription(), "some description");
        assertEquals(actual.getRating(), 10.0, 0);
        assertEquals(actual.getPrice(), 330.0, 0);
        assertEquals(actual.getPicturePath(), "http:somepath");
        assertNull(actual.getGenres());
        assertNull(actual.getCountries());
        assertNull(actual.getReviews());
    }

    @Test
    public void testToMovieDtoForUpdate() {
        String json = "{ \"nameRussian\" : \"название\", \"nameNative\" : \"testName\", \"yearOfRelease\" : 2000," +
                      "\"description\" : \"some description\"," +
                      "\"rating\" : 10.0, \"price\" : 330," +
                      "\"countries\" : [1,2,3]," +
                      "\"genres\" : [4,5,6,7]," +
                      "\"picturePath\" : \"http:somepath\" }";

        MovieDtoForUpdate actual = toMovieDtoForUpdate(json);
        assertEquals(actual.getCountries().length, 3);
        assertEquals(actual.getGenres().length, 4);
    }

}
