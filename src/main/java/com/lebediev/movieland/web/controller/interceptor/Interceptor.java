package com.lebediev.movieland.web.controller.interceptor;

import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.authentication.UserToken;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class Interceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logRequestId(request);
        return super.preHandle(request, response, handler);
    }


    private String authorize(String uuid) {
        try {
            UserToken userToken = authService.authorize(UUID.fromString(uuid));
            return userToken.getUser().getEmail();
        } catch (SecurityException e) {
            return "guest";
        }
    }

    private void logRequestId(HttpServletRequest request) {
        String uuid = request.getHeader("uuid");
        String currentUser = "guest";
        if (uuid != null) {
            currentUser = authorize(uuid);
        }
        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("currentUser", currentUser);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}