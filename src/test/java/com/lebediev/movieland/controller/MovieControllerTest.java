package com.lebediev.movieland.controller;

import com.lebediev.movieland.controller.dto.movie.MovieDTOConverter;
import com.lebediev.movieland.controller.utils.JsonConverter;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieControllerTest {
    @Mock
    MovieService movieService;
    @Mock
    MovieDTOConverter movieDTOConverter;
    @Mock
    JsonConverter jsonConverter;

    @InjectMocks
    MovieController movieController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void testGetAllMovies() throws Exception {
        Genre genre = mock(Genre.class);
        Country country = mock(Country.class);
        Movie movieOne = new Movie(44, "testMovieNameRus", "testMovieNameNative",
                                   1999, "testDescription", 0.1, 2.2, Arrays.asList(genre),
                                   Arrays.asList(country), "testPoster");
        Movie movieTwo = new Movie(88, "testMovieNameRusTwo", "testMovieNameNativeTwo",
                                   2050, "testDescriptionTwo", 4.4, 5.0, Arrays.asList(genre),
                                   Arrays.asList(country), "testPosterTwo");
        List <Movie> movieList = Arrays.asList(movieOne, movieTwo);
        when(movieService.getAllMovies()).thenReturn(movieList);
        System.out.println(jsonConverter.toJson(movieDTOConverter.toMovieDTOList(movieService.getAllMovies())));

        mockMvc.perform(get("/v1/movie")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].movieId", is(44))).
                andExpect(jsonPath("$[1].movieNameNative", is("testMovieNameNativeTwo")));
    }
}
