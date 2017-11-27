package com.lebediev.movieland.dao.jdbc.integrationtests;


import com.lebediev.movieland.dao.CountryDao;
import com.lebediev.movieland.dao.MovieDao;
import com.lebediev.movieland.dao.jdbc.JdbcMovieDao;
import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class JdbcCountryDaoITest {

    @Autowired
    private CountryDao countryDao;
    @Autowired
    private MovieDao movieDao;


    @Test
    public void getMovieToCountryMappings() {
        List <Movie> movieList = movieDao.getAll(new SortParams());
        List <Integer> params = Arrays.asList(movieList.get(0).getId(),
                                              movieList.get(1).getId(),
                                              movieList.get(2).getId());
        List <MovieToCountry> movieToCountries = countryDao.getMovieToCountryMappings(params);
        assertNotEquals(movieToCountries.size(), 0);
    }

    @Test
    public void testGetAll(){
        List<Country> countryList = countryDao.getAll();
        assertNotEquals(countryList.size(),0);
    }

}
