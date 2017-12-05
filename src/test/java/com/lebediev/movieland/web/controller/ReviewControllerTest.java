package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.service.ReviewService;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.authentication.UserToken;
import com.lebediev.movieland.web.controller.interceptor.AuthInterceptor;
import com.lebediev.movieland.web.controller.utils.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthInterceptor authInterceptor ;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).setControllerAdvice(new GlobalExceptionHandler()).
                addInterceptors(authInterceptor).build();
        User user = new User(1, "nickname", "email", "password", Arrays.asList(Role.USER));
        User admin = new User(1, "nickname", "email", "password", Arrays.asList(Role.ADMIN));
        UserToken userTokenOne = new UserToken(UUID.randomUUID(), LocalDateTime.now(), user);
        UserToken userTokenTwo = new UserToken(UUID.randomUUID(), LocalDateTime.now(), admin);
        when(authService.authorize(UUID.fromString("096f33e2-a224-3aed-9f93-a82fc74549fe"))).thenReturn(userTokenOne);
        when(authService.getCurrentUser()).thenReturn(user);
        when(authService.authorize(UUID.fromString("096f33e2-a335-3aed-9f93-a82fc74549fe"))).thenReturn(userTokenTwo);
        when(authService.getCurrentUser()).thenReturn(admin);
    }

    @Test
    public void testAdd() throws Exception {
        mockMvc.perform(post("/review").content("{ \"movieId\" : 1,\"text\" : \"some review\" }").
                header("uuid", "096f33e2-a224-3aed-9f93-a82fc74549fe")).andExpect(status().isOk());

        mockMvc.perform(post("/review").content("{ \"movieId\" : 1,\"text\" : \"some review\" }").
                header("uuid", "096f33e2-a335-3aed-9f93-a82fc74549fe")).andExpect(status().isBadRequest());

        mockMvc.perform(post("/review").content("{ \"movieId\" : 1,\"text\" : \"some review\" }")
                ).andExpect(status().isBadRequest());
    }
}
