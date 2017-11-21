package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.authentication.UserToken;
import com.lebediev.movieland.web.controller.utils.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {

    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserToken userToken = new UserToken(UUID.randomUUID(), LocalDateTime.now(), new User());
        mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(new GlobalExceptionHandler()).build();
        when(authService.authenticate("ronald.reynolds66@example.com", "paco")).thenReturn(userToken);
        when(authService.authenticate("ronald.reynolds66@example.com", "wrong")).thenThrow(IllegalArgumentException.class);
        when(authService.deleteToken(UUID.fromString("096f33e2-a224-3aed-9f93-a82fc74549fe"))).thenReturn(true);
    }


    @Test
    public void testLogin() throws Exception {

        mockMvc.perform(post("/login").content("{ \"email\" : \"ronald.reynolds66@example.com\",\"password\" : \"paco\" }")).
                andExpect(status().isOk());

        mockMvc.perform(post("/login").content("{ \"email\" : \"ronald.reynolds66@example.com\",\"password\" : \"wrong\" }")).
                andExpect(status().isBadRequest());
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(delete("/logout").header("uuid", "096f33e2-a224-3aed-9f93-a82fc74549fe")).
                andExpect(status().isOk());
    }

}
