package com.lebediev.movieland.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.controller.dto.MovieDto;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class JsonConverterTest {

    @Test
    public void testToJson() throws JsonProcessingException {
        JsonConverter jsonConverter = new JsonConverter();
        List <MovieDto> MovieDtoList = new ArrayList <>();
        Genre genre = new Genre(1,"криминал");
        Country country = new Country(2,"someCountry");
        MovieDto movieDto = new MovieDto(44, "testMovieNameRus", "testMovieNameNative",
                                         1999, "testDescription", 0.1, 2.2, Arrays.asList(genre),
                                         Arrays.asList(country), "testPoster");
        MovieDtoList.add(movieDto);
        String actual = jsonConverter.toJson(MovieDtoList, JsonConverter.JsonView.BASE);
        String expected = "[{\"movieId\":44,\"movieNameRus\":\"testMovieNameRus\",\"movieNameNative\":\"testMovieNameNative\"," +
                "\"date\":1999,\"rating\":0.1,\"price\":2.2,\"poster\":\"testPoster\"}]";
        assertEquals(expected, actual);

        expected = "[{\"movieId\":44,\"movieNameRus\":\"testMovieNameRus\",\"movieNameNative\":\"testMovieNameNative\"," +
                "\"date\":1999,\"description\":\"testDescription\",\"rating\":0.1,\"price\":2.2," +
                "\"countries\":[{\"countryId\":2,\"countryName\":\"someCountry\"}],\"genres\":[{\"genreId\":1," +
                "\"genreName\":\"криминал\"}],\"poster\":\"testPoster\"}]";
        actual = jsonConverter.toJson(MovieDtoList, JsonConverter.JsonView.EXTENDED);
        assertEquals(expected, actual);
        }

}
