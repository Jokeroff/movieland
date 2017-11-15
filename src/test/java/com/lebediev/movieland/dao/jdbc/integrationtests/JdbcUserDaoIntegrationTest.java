package com.lebediev.movieland.dao.jdbc.integrationtests;

import com.lebediev.movieland.dao.jdbc.JdbcUserDao;
import com.lebediev.movieland.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-config.xml"})
public class JdbcUserDaoIntegrationTest {
    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Test
    public void testGetUserById() {
        User actual = jdbcUserDao.getUserById(1);
        assertNotEquals(actual, null);
    }
}
