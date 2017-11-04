package com.lebediev.movieland.web.controller;

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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieControllerTest {
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        Genre genre = new Genre (33, "testGenre");
        Country country = new Country(55, "testCountry");
        Movie movieOne = new Movie();
        movieOne.setMovieId(44);
        movieOne.setMovieNameRus("testMovieNameRus");
        movieOne.setMovieNameNative("testMovieNameNative");
        movieOne.setDate(1999);
        movieOne.setDescription("testDescription");
        movieOne.setRating(0.1);
        movieOne.setPrice(2.2);
        movieOne.setGenres(Arrays.asList(genre));
        movieOne.setCountries(Arrays.asList(country));
        movieOne.setPoster("testPoster");

        Movie movieTwo = new Movie();
        movieTwo.setMovieId(88);
        movieTwo.setMovieNameRus("testMovieNameRusTwo");
        movieTwo.setMovieNameNative("testMovieNameNativeTwo");
        movieTwo.setDate(2050);
        movieTwo.setDescription("testDescriptionTwo");
        movieTwo.setRating(4.4);
        movieTwo.setPrice(5.0);
        movieTwo.setGenres(Arrays.asList(genre));
        movieTwo.setCountries(Arrays.asList(country));
        movieTwo.setPoster("testPosterTwo");

        List <Movie> movieList = Arrays.asList(movieOne, movieTwo);
        when(movieService.getAllMovies()).thenReturn(movieList);
        when(movieService.getRandomMovies()).thenReturn(movieList);
    }

    @Test
    public void testGetAllMovies() throws Exception {

        mockMvc.perform(get("/v1/movie")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].movieId", is(44))).
                andExpect(jsonPath("$[0].movieNameNative", is("testMovieNameNative"))).
                andExpect(jsonPath("$[0].movieNameRus", is("testMovieNameRus"))).
                andExpect(jsonPath("$[0].date", is(1999))).
                andExpect(jsonPath("$[0].rating", is(0.1))).
                andExpect(jsonPath("$[0].price", is(2.2))).
                andExpect(jsonPath("$[0].poster", is("testPoster"))).
                andExpect(jsonPath("$[1].movieId", is(88))).
                andExpect(jsonPath("$[1].movieNameNative", is("testMovieNameNativeTwo"))).
                andExpect(jsonPath("$[1].movieNameRus", is("testMovieNameRusTwo"))).
                andExpect(jsonPath("$[1].date", is(2050))).
                andExpect(jsonPath("$[1].rating", is(4.4))).
                andExpect(jsonPath("$[1].price", is(5.0))).
                andExpect(jsonPath("$[1].poster", is("testPosterTwo")));
    }

    @Test
    public void testGetRandomMovies() throws Exception {

        mockMvc.perform(get("/v1/movie/random")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].movieId", is(44))).
                andExpect(jsonPath("$[0].movieNameNative", is("testMovieNameNative"))).
                andExpect(jsonPath("$[0].movieNameRus", is("testMovieNameRus"))).
                andExpect(jsonPath("$[0].date", is(1999))).
                andExpect(jsonPath("$[0].rating", is(0.1))).
                andExpect(jsonPath("$[0].price", is(2.2))).
                andExpect(jsonPath("$[0].poster", is("testPoster"))).
                andExpect(jsonPath("$[1].movieId", is(88))).
                andExpect(jsonPath("$[1].movieNameNative", is("testMovieNameNativeTwo"))).
                andExpect(jsonPath("$[1].movieNameRus", is("testMovieNameRusTwo"))).
                andExpect(jsonPath("$[1].date", is(2050))).
                andExpect(jsonPath("$[1].rating", is(4.4))).
                andExpect(jsonPath("$[1].price", is(5.0))).
                andExpect(jsonPath("$[1].poster", is("testPosterTwo")));
    }
}
