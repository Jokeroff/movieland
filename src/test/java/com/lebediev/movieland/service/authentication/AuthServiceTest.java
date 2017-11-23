package com.lebediev.movieland.service.authentication;

import com.lebediev.movieland.dao.UserDao;
import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Spy
    private UserDao userDao;
    @InjectMocks
    private AuthService authService;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        String password = BCrypt.hashpw("testPassword", BCrypt.gensalt());
        user = new User(1, "testNickname", "testEmail", password, Arrays.asList(Role.USER));
        when(userDao.getByEmail(anyString())).thenReturn(user);
    }


    @Test
    public void testAuthenticate() {

        UserToken userToken = authService.authenticate("testEmail", "testPassword");
        assertTrue(userToken.getUser().equals(user));

        UserToken userTokenFromCache = authService.authenticate("testEmail", "testPassword");
        assertTrue(userToken.equals(userTokenFromCache));

        thrown.expect(SecurityException.class);
        authService.authenticate("testEmail", "wrongPassword");
    }

    @Test
    public void testDeleteToken() {
        UserToken userToken = authService.authenticate("testEmail", "testPassword");

        UUID uuid = userToken.getUuid();
        assertTrue(authService.deleteToken(uuid));

        UserToken userTokenNew = authService.authenticate("testEmail", "testPassword");
        assertNotEquals(userToken, userTokenNew);

        assertFalse(authService.deleteToken(UUID.randomUUID()));
    }

    @Test
    public void testAuthorize() {
        UserToken userToken = authService.authenticate("testEmail", "testPassword");

        UUID uuid = userToken.getUuid();
        assertEquals(userToken, authService.authorize(uuid));

        thrown.expect(SecurityException.class);
        authService.authorize(UUID.randomUUID());
    }

}

