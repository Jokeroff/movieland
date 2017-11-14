package com.lebediev.movieland.service.enrich;

import com.lebediev.movieland.dao.jdbc.JdbcCountryDao;
import com.lebediev.movieland.dao.jdbc.JdbcGenreDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import com.lebediev.movieland.dao.jdbc.entity.MovieToGenre;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.entity.Movie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

public class MovieEnrichmentServiceTest {

    @Spy
    private JdbcGenreDao jdbcGenreDao;
    @Spy
    private JdbcCountryDao jdbcCountryDao;
    @InjectMocks
    private MovieEnrichmentService movieEnrichmentService;
    @Spy
    private Movie movieOne;
    @Spy
    private Movie movieTwo;

    private List <Movie> movieList;
    private List <MovieToGenre> movieToGenreList;
    private List <MovieToCountry> movieToCountryList;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        movieOne.setMovieId(1);
        movieTwo.setMovieId(88);
        movieList = Arrays.asList(movieOne, movieTwo);
        movieToGenreList = Arrays.asList(new MovieToGenre(1, 1, "criminal"),
                                         new MovieToGenre(1, 2, "drama"));
        movieToCountryList = Arrays.asList(new MovieToCountry(1, 1, "Poland"),
                                           new MovieToCountry(1, 2, "Belgium"));
    }

    @Test
    public void testEnrichMovieByGenres() {
        assertNotEquals(movieList.size(), 0);

        doReturn(movieToGenreList).when(jdbcGenreDao).getMovieToGenreMappings(anyList());
        movieEnrichmentService.enrichMovieByGenres(movieList);
        assertEquals(movieList.get(0).getGenres().get(0).getGenreName(), "criminal");
        assertEquals(movieList.get(0).getGenres().get(1).getGenreName(), "drama");
    }

    @Test
    public void testEnrichMovieByCountries() {
        assertNotEquals(movieList.size(), 0);
        doReturn(movieToCountryList).when(jdbcCountryDao).getMovieToCountryMappings(anyList());
        movieEnrichmentService.enrichMovieByCountries(movieList);
        assertEquals(movieList.get(0).getCountries().get(0).getCountryName(), "Poland");
        assertEquals(movieList.get(0).getCountries().get(1).getCountryName(), "Belgium");
    }

    @Test
    public void testGetGenresByMovieId() {
        List <Genre> genreList = movieEnrichmentService.getGenresByMovieId(movieToGenreList, 1);
        assertNotEquals(genreList, 0);
        assertEquals(genreList.get(0).getGenreName(), "criminal");
        assertEquals(genreList.get(1).getGenreName(), "drama");
    }

    @Test
    public void testGetCountriesByMovieId() {
        List <Country> countryList = movieEnrichmentService.getCountriesByMovieId(movieToCountryList, 1);
        assertNotEquals(countryList, 0);
        assertEquals(countryList.get(0).getCountryName(), "Poland");
        assertEquals(countryList.get(1).getCountryName(), "Belgium");
    }
}
