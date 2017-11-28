package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.service.MovieService;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.web.controller.interceptor.AuthInterceptor;
import com.lebediev.movieland.web.controller.utils.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieControllerTest {
    @Mock
    private MovieService movieService;
    @Mock
    private AuthService authService;
    @Spy
    private AuthInterceptor authInterceptor;
    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;
    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).setControllerAdvice(new GlobalExceptionHandler()).
                addInterceptors(authInterceptor).build();
        Genre genre = new Genre(3, "testGenre");
        Country country = new Country(55, "testCountry");
        Movie movieOne = new Movie();
        movieOne.setId(44);
        movieOne.setNameRussian("testNameRussian");
        movieOne.setNameNative("testNameNative");
        movieOne.setYearOfRelease(1999);
        movieOne.setDescription("testDescription");
        movieOne.setRating(0.1);
        movieOne.setPrice(2.2);
        movieOne.setGenres(Arrays.asList(genre));
        movieOne.setCountries(Arrays.asList(country));
        movieOne.setPicturePath("testPicturePath");

        Movie movieTwo = new Movie();
        movieTwo.setId(88);
        movieTwo.setNameRussian("testNameRussianTwo");
        movieTwo.setNameNative("testNameNativeTwo");
        movieTwo.setYearOfRelease(2050);
        movieTwo.setDescription("testDescriptionTwo");
        movieTwo.setRating(4.4);
        movieTwo.setPrice(5.0);
        movieTwo.setGenres(Arrays.asList(genre));
        movieTwo.setCountries(Arrays.asList(country));
        movieTwo.setPicturePath("testPicturePathTwo");
        List <Movie> movieList = Arrays.asList(movieOne, movieTwo);

        when(movieService.getAllMovies(any(SortParams.class))).thenReturn(movieList);
        when(movieService.getMoviesByGenreId(anyInt(), any(SortParams.class))).thenReturn(movieList);
        when(movieService.getRandomMovies()).thenReturn(movieList);
        when(movieService.getMovieById(anyInt())).thenReturn(movieOne);

        user = new User(1, "nickname", "email", "password", Arrays.asList(Role.USER));
        User admin = new User(1, "nickname", "email", "password", Arrays.asList(Role.ADMIN));

    }

    @Test
    public void testGetAllMovies() throws Exception {

        mockMvc.perform(get("/movie")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].id", is(44))).
                andExpect(jsonPath("$[0].nameNative", is("testNameNative"))).
                andExpect(jsonPath("$[0].nameRussian", is("testNameRussian"))).
                andExpect(jsonPath("$[0].yearOfRelease", is(1999))).
                andExpect(jsonPath("$[0].rating", is(0.1))).
                andExpect(jsonPath("$[0].price", is(2.2))).
                andExpect(jsonPath("$[0].picturePath", is("testPicturePath"))).
                andExpect(jsonPath("$[1].id", is(88))).
                andExpect(jsonPath("$[1].nameNative", is("testNameNativeTwo"))).
                andExpect(jsonPath("$[1].nameRussian", is("testNameRussianTwo"))).
                andExpect(jsonPath("$[1].yearOfRelease", is(2050))).
                andExpect(jsonPath("$[1].rating", is(4.4))).
                andExpect(jsonPath("$[1].price", is(5.0))).
                andExpect(jsonPath("$[1].picturePath", is("testPicturePathTwo")));

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
                andExpect(jsonPath("$[0].id", is(44))).
                andExpect(jsonPath("$[0].nameNative", is("testNameNative"))).
                andExpect(jsonPath("$[0].nameRussian", is("testNameRussian"))).
                andExpect(jsonPath("$[0].yearOfRelease", is(1999))).
                andExpect(jsonPath("$[0].rating", is(0.1))).
                andExpect(jsonPath("$[0].price", is(2.2))).
                andExpect(jsonPath("$[0].picturePath", is("testPicturePath"))).
                andExpect(jsonPath("$[1].id", is(88))).
                andExpect(jsonPath("$[1].nameNative", is("testNameNativeTwo"))).
                andExpect(jsonPath("$[1].nameRussian", is("testNameRussianTwo"))).
                andExpect(jsonPath("$[1].yearOfRelease", is(2050))).
                andExpect(jsonPath("$[1].rating", is(4.4))).
                andExpect(jsonPath("$[1].price", is(5.0))).
                andExpect(jsonPath("$[1].picturePath", is("testPicturePathTwo")));
    }

    @Test
    public void testGetMoviesByGenreId() throws Exception {
        mockMvc.perform(get("/movie/genre/3")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].id", is(44))).
                andExpect(jsonPath("$[0].nameNative", is("testNameNative"))).
                andExpect(jsonPath("$[0].nameRussian", is("testNameRussian"))).
                andExpect(jsonPath("$[0].yearOfRelease", is(1999))).
                andExpect(jsonPath("$[0].rating", is(0.1))).
                andExpect(jsonPath("$[0].price", is(2.2))).
                andExpect(jsonPath("$[0].picturePath", is("testPicturePath"))).
                andExpect(jsonPath("$[1].id", is(88))).
                andExpect(jsonPath("$[1].nameNative", is("testNameNativeTwo"))).
                andExpect(jsonPath("$[1].nameRussian", is("testNameRussianTwo"))).
                andExpect(jsonPath("$[1].yearOfRelease", is(2050))).
                andExpect(jsonPath("$[1].rating", is(4.4))).
                andExpect(jsonPath("$[1].price", is(5.0))).
                andExpect(jsonPath("$[1].picturePath", is("testPicturePathTwo")));

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
                andExpect(jsonPath("$.id", is(44)));
    }

    @Test
    public void testAdd() throws Exception {
        mockMvc.perform(post("/movie").content("{ \"nameRussian\" : \"название\", \"nameNative\" : \"testName\", \"yearOfRelease\" : 2000," +
                                               "\"description\" : \"some description\"," +
                                               "\"rating\" : 10.0, \"price\" : 330,\"picturePath\" : \"http:somepath\" }").
                header("uuid", "096f33e2-a335-3aed-9f93-a82fc74549fe")).andExpect(status().isOk());

        mockMvc.perform(post("/movie").content("{ \"nameRussian\" : \"название\", \"nameNative\" : \"testName\", \"yearOfRelease\" : 2000," +
                                               "\"description\" : \"some description\"," +
                                               "\"rating\" : 10.0, \"price\" : 330," +
                "\"countries\" : [1,2,3], \"genres\" : [4,5,6,7], \"picturePath\" : \"http:somepath\" }").
                header("uuid", "096f33e2-a224-3aed-9f93-a82fc74549fe")).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(put("/movie/1").content("{ \"nameRussian\" : \"название\", \"nameNative\" : \"testName\", \"yearOfRelease\" : 2000," +
                                               "\"description\" : \"some description\"," +
                                               "\"rating\" : 10.0, \"price\" : 330,\"picturePath\" : \"http:somepath\" }").
                header("uuid", "096f33e2-a335-3aed-9f93-a82fc74549fe")).andExpect(status().isOk());

        mockMvc.perform(put("/movie/1").content("{ \"nameRussian\" : \"название\", \"nameNative\" : \"testName\", \"yearOfRelease\" : 2000," +
                                               "\"description\" : \"some description\"," +
                                               "\"rating\" : 10.0, \"price\" : 330,\"picturePath\" : \"http:somepath\" }").
                header("uuid", "096f33e2-a224-3aed-9f93-a82fc74549fe")).andExpect(status().isBadRequest());

        mockMvc.perform(put("/movie/1").content("{  }").
                header("uuid", "096f33e2-a224-3aed-9f93-a82fc74549fe")).andExpect(status().isBadRequest());

        mockMvc.perform(put("/movie/1").content("{  }")).andExpect(status().isBadRequest());
    }

}
