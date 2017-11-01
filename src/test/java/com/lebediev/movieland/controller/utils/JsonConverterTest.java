package com.lebediev.movieland.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.controller.dto.movie.MovieDTO;
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
        List <MovieDTO> MovieDTOList = new ArrayList <>();
        Genre genre = new Genre();
        Country country = new Country();
        MovieDTO movieDTO = new MovieDTO(44, "testMovieNameRus", "testMovieNameNative",
                                         1999, "testDescription", 0.1, 2.2, Arrays.asList(genre),
                                         Arrays.asList(country), "testPoster");
        MovieDTOList.add(movieDTO);
        String actual = jsonConverter.toJson(MovieDTOList, JsonConverter.JsonView.BASE);
        String expected = "[{\"movieId\":44,\"movieNameRus\":\"testMovieNameRus\",\"movieNameNative\":\"testMovieNameNative\"," +
                "\"date\":1999,\"rating\":0.1,\"price\":2.2,\"poster\":\"testPoster\"}]";
        assertEquals(expected, actual);

        expected = "[{\"movieId\":44,\"movieNameRus\":\"testMovieNameRus\",\"movieNameNative\":\"testMovieNameNative\"," +
                "\"date\":1999,\"description\":\"testDescription\",\"rating\":0.1,\"price\":2.2," +
                "\"countries\":[{\"countryId\":0,\"countryName\":null}],\"genres\":[{\"genreId\":0," +
                "\"genreName\":null}],\"poster\":\"testPoster\"}]";
        actual = jsonConverter.toJson(MovieDTOList, JsonConverter.JsonView.EXTENDED);
        assertEquals(expected, actual);
        }

}
