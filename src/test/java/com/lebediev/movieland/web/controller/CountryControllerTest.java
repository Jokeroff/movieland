package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.service.CountryService;
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

public class CountryControllerTest {

        @Mock
        private CountryService countryService;
        @InjectMocks
        private CountryController countryController;

        private MockMvc mockMvc;

        @Before
        public void setup() {
            MockitoAnnotations.initMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
            Country countryOne = new Country(1, "Ukraine");
            Country countryTwo = new Country(2, "Uganda");
            List<Country> countryList = Arrays.asList(countryOne, countryTwo);
            when(countryService.getAll()).thenReturn(countryList);
        }

        @Test
        public void testGetAllGenres() throws Exception {
            mockMvc.perform(get("/country")).andExpect(status().isOk()).
                    andExpect(jsonPath("$", hasSize(2))).
                    andExpect(jsonPath("$[0].id", is(1))).
                    andExpect(jsonPath("$[0].name", is("Ukraine"))).
                    andExpect(jsonPath("$[1].id", is(2))).
                    andExpect(jsonPath("$[1].name", is("Uganda")));
        }
    }
