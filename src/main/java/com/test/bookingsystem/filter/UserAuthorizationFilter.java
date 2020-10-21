package com.test.bookingsystem.filter;

import com.test.bookingsystem.model.persistence.User;
import com.test.bookingsystem.service.JWTService;
import com.test.bookingsystem.service.UserService;
import com.test.bookingsystem.util.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class UserAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = httpServletRequest.getRequestURI();
        if( requestPath.startsWith(ResourcePath.USERS_PATH) ) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Optional<String> jwtTokenOptional = extractJWTToken(httpServletRequest);
        if (jwtTokenOptional.isPresent() && jwtService.isValid(jwtTokenOptional.get())) {
            Optional<User> userOptional = getUserFromToken(jwtTokenOptional.get());
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                if (canPerformCurrentAction(requestPath, httpServletRequest.getMethod(), user)) {
                    setUserInThreadLocal(user);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    resetUserThreadLocal();
                    return;
                } else {
                    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            }
        }
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    private boolean canPerformCurrentAction(String uri, String httpMethod, User user) {
        return user.isAdmin() || ResourcePath.isUserPermittedAction(uri, httpMethod);
    }

    private Optional<String> extractJWTToken(HttpServletRequest request) {
        String header= request.getHeader(AUTHORIZATION_HEADER);
        if(header != null) {
            return Optional.of(header.substring(BEARER_PREFIX.length()));
        }
        return Optional.empty();
    }

    private Optional<User> getUserFromToken(String token) {
        Long userId = Long.parseLong(jwtService.getSubject(token));
        return userService.findUserById(userId);
    }

    private void setUserInThreadLocal(User user){
        userService.resetCurrentUser();
        userService.setCurrentUser(user);
    }

    private void resetUserThreadLocal() {
        userService.resetCurrentUser();
    }

}
