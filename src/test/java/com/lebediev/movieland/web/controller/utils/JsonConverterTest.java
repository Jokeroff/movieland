package com.lebediev.movieland.web.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.dto.ReviewDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toJson;
import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    private final List <MovieDto> MovieDtoList = new ArrayList <>();
    private List <Genre> genreList;

    @Test
    public void testToJsonMovie() throws JsonProcessingException {

        Country country = new Country(2, "someCountry");
        MovieDto movieDto = new MovieDto(44, "testMovieNameRus", "testMovieNameNative",
                                         1999, "testDescription", 0.1, 2.2, genreList,
                                         Arrays.asList(country), "testPoster", Arrays.asList(new ReviewDto()));
        MovieDtoList.add(movieDto);

        String actual = toJson(MovieDtoList, JsonConverter.JsonView.BASE);
        String expected = "[{\"movieId\":44,\"movieNameRus\":\"testMovieNameRus\",\"movieNameNative\":\"testMovieNameNative\"," +
                          "\"date\":1999,\"rating\":0.1,\"price\":2.2,\"poster\":\"testPoster\"}]";
        assertEquals(expected, actual);

        expected = "[{\"movieId\":44,\"movieNameRus\":\"testMovieNameRus\",\"movieNameNative\":\"testMovieNameNative\"," +
                   "\"date\":1999,\"description\":\"testDescription\",\"rating\":0.1,\"price\":2.2," +
                   "\"countries\":[{\"countryId\":2,\"countryName\":\"someCountry\"}],\"genres\":[{\"genreId\":1," +
                   "\"genreName\":\"криминал\"},{\"genreId\":2,\"genreName\":\"drama\"}],\"poster\":\"testPoster\"}]";
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
        String expected = "[{\"genreId\":1,\"genreName\":\"криминал\"},{\"genreId\":2,\"genreName\":\"drama\"}]";
        assertEquals(expected, actual);
    }

}
