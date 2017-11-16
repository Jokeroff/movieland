package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.service.MovieService;
import com.lebediev.movieland.service.conversion.CachedCurrency;
import com.lebediev.movieland.service.conversion.Currency;
import com.lebediev.movieland.service.conversion.CurrencyConverter;
import com.lebediev.movieland.service.conversion.CurrencyEntity;
import com.lebediev.movieland.web.controller.utils.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieControllerTest {
    @Mock
    private MovieService movieService;
    @Spy
    private CachedCurrency cachedCurrency;
    @Spy
    private CurrencyConverter currencyConverter;
    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).setControllerAdvice(new GlobalExceptionHandler()).build();
        Genre genre = new Genre(3, "testGenre");
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

        when(movieService.getAllMovies(any(SortParams.class))).thenReturn(movieList);
        when(movieService.getMoviesByGenreId(anyInt(), any(SortParams.class))).thenReturn(movieList);
        when(movieService.getRandomMovies()).thenReturn(movieList);
        when(movieService.getMovieById(anyInt())).thenReturn(movieOne);
        CurrencyEntity currencyEntity = new CurrencyEntity(Currency.EUR, 30.44, LocalDate.now());
        doReturn(currencyEntity).when(cachedCurrency).getCurrencyEntity(Currency.EUR);
       // when(cachedCurrency.getCurrencyEntity(Currency.EUR)).thenReturn(currencyEntity);

    }

    @Test
    public void testGetAllMovies() throws Exception {

        mockMvc.perform(get("/movie")).
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

// with various params (order by)

        mockMvc.perform(get("/movie").param("rating", "desc")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/movie").param("price", "desc")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/movie").param("price", "asc")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2)));
// not allowed
        mockMvc.perform(get("/movie").param("rating", "asc")).andExpect(status().isBadRequest());

        mockMvc.perform(get("/movie").param("rating", "asc").
                param("price", "asc")).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRandomMovies() throws Exception {

        mockMvc.perform(get("/movie/random")).
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
    public void testGetMoviesByGenreId() throws Exception {
        mockMvc.perform(get("/movie/genre/3")).andExpect(status().isOk()).
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

        // with various params (order by)

        mockMvc.perform(get("/movie/genre/3").param("rating", "desc")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/movie/genre/4").param("price", "desc")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/movie/genre/5").param("price", "asc")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/movie/genre/3").param("wrong", "param")).andExpect(status().isBadRequest());

        mockMvc.perform(get("/movie/genre/3").param("rating", "asc").
                param("price", "asc")).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetMovieById() throws Exception {
        mockMvc.perform(get("/movie/1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.movieId", is(44)));
    }

}
