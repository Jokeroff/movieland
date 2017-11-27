package com.lebediev.movieland.web.controller.interceptor;

import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.authentication.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.lebediev.movieland.service.authentication.AuthService.clearUserThreadLocal;
import static com.lebediev.movieland.service.authentication.AuthService.setUserThreadLocal;

@Service
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = request.getHeader("uuid");
        if (uuid != null) {
            try{
                UserToken userToken = authService.authorize(UUID.fromString(uuid));
                setUserThreadLocal(userToken.getUser());
            } catch (SecurityException e) {
                throw new RuntimeException("Authentication failed: ", e);
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        clearUserThreadLocal();
    }
}