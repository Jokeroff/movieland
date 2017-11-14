package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.service.GenreService;
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

public class GenreControllerTest {
    @Mock
    private GenreService genreService;
    @InjectMocks
    private GenreController genreController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
        Genre genreOne = new Genre(11, "testGenreOne");
        Genre genreTwo = new Genre(22, "testGenreTwo");
        List <Genre> genreList = Arrays.asList(genreOne, genreTwo);
        when(genreService.getAllGenres()).thenReturn(genreList);
    }

    @Test
    public void testGetAllGenres() throws Exception {
        mockMvc.perform(get("/genre")).andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].genreId", is(11))).
                andExpect(jsonPath("$[0].genreName", is("testGenreOne"))).
                andExpect(jsonPath("$[1].genreId", is(22))).
                andExpect(jsonPath("$[1].genreName", is("testGenreTwo")));
    }
}
