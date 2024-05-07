package com.springsecurity.springsecurity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class RobotAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public RobotAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Optional<String> robotpassword = Optional.ofNullable(request.getHeader("x-robot-password"));
        if (robotpassword.isPresent()) {
            RobotAuthenticationToken authRequest = RobotAuthenticationToken.unauthenticated(robotpassword.get());
            try {
                var authenticatedRequest = authenticationManager.authenticate(authRequest);
                SecurityContext newContext = SecurityContextHolder.createEmptyContext();
                newContext.setAuthentication(authenticatedRequest);
                SecurityContextHolder.setContext(newContext);
            } catch (AuthenticationException e) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Stop pretending you are robot!");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
