package com.lebediev.movieland.dao.jdbc.integrationtests;


import com.lebediev.movieland.dao.jdbc.impl.CountryDAOJdbc;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.MovieToCountry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class CountryDAOJdbcIntegrationTest {

    @Autowired
    CountryDAOJdbc countryDAOJdbc;

    @Test
    public void getMovieToCountryMappings(){
        List<MovieToCountry> movieToCountries = countryDAOJdbc.getMovieToCountryMappings();
        assertNotEquals(movieToCountries.size(), 0);
    }

    @Test
    public void testGetGenresByMovieId(){
        List<MovieToCountry> movieToCountries = countryDAOJdbc.getMovieToCountryMappings();
        List<Country> countryList = countryDAOJdbc.getCountriesByMovieId(movieToCountries, movieToCountries.get(0).getMovieId());
        assertNotEquals(countryList, 0);
    }
}
