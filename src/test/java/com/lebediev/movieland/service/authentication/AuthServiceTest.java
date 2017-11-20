package com.lebediev.movieland.service.authentication;

import com.lebediev.movieland.dao.UserDao;
import com.lebediev.movieland.entity.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Spy
    UserDao userDao;
    @InjectMocks
    AuthService authService;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCheckPassword() {
        String password = BCrypt.hashpw("testPassword", BCrypt.gensalt());
        User user = new User(1, "testNickname", "testEmail", password);
        when(userDao.getUserByEmail(anyString())).thenReturn(user);

      /*  assertEquals(user, authService.checkPassword("dummyEmail", "testPassword"));


        thrown.expect(IllegalArgumentException.class);

        user = authService.checkPassword("dummyEmail", "wrongPassword");*/
    }
}

