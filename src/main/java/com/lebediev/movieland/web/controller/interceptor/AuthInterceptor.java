package com.lebediev.movieland.web.controller.interceptor;


import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.User;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.authentication.UserToken;
import com.lebediev.movieland.web.controller.utils.RoleRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Service
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Role requiredRole = null;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RoleRequired annotation = handlerMethod.getMethod().getAnnotation(RoleRequired.class);
        if (annotation != null) {
            requiredRole =  annotation.role();
        }

        String uuid = request.getHeader("uuid");

        if (uuid == null && requiredRole != null) {
            LOG.error("Required role not assigned to current user");
            throw new SecurityException("Required role not assigned to current user");
        }

        if (uuid != null) {
            UserToken userToken = authService.authorize(UUID.fromString(uuid));
                User user = userToken.getUser();
                if (requiredRole != null) {
                    checkRequiredRole(user, requiredRole);
                }
                authService.setCurrentUser(user);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        authService.clearUserThreadLocal();
    }

    private void checkRequiredRole(User user, Role role) {
        if (!(user.getRoles().contains(role))) {
            LOG.error("Required role not assigned to current user");
            throw new SecurityException("Required role not assigned to current user");
        }

    }


}