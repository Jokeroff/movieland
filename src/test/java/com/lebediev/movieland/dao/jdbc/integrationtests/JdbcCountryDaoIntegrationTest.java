package com.lebediev.movieland.dao.jdbc.integrationtests;


import com.lebediev.movieland.dao.jdbc.impl.JdbcCountryDao;
import com.lebediev.movieland.entity.Country;
import com.lebediev.movieland.dao.jdbc.entity.MovieToCountry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class JdbcCountryDaoIntegrationTest {

    @Autowired
    JdbcCountryDao jdbcCountryDao;

    @Test
    public void getMovieToCountryMappings(){
        List<MovieToCountry> movieToCountries = jdbcCountryDao.getMovieToCountryMappings();
        assertNotEquals(movieToCountries.size(), 0);
    }

}
