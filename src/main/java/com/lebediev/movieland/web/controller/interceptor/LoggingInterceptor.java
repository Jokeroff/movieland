package com.lebediev.movieland.web.controller.interceptor;

import com.lebediev.movieland.entity.User;
import org.slf4j.MDC;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.lebediev.movieland.service.authentication.AuthService.getUserThreadLocal;

public class LoggingInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = getUserThreadLocal();
        String userName = "guest";
        if (user != null) {
            userName = user.getEmail();
        }
        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("currentUser", userName);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
        super.afterCompletion(request, response, handler, ex);
    }
}
