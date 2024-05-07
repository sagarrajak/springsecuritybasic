package com.springsecurity.springsecurity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class RobotAuthenticationProvider implements AuthenticationProvider {
    final  List<String> passwords;

    public RobotAuthenticationProvider() {
        this.passwords = new ArrayList<>();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RobotAuthenticationToken robotAuthenticationToken = (RobotAuthenticationToken) authentication;
        String password = robotAuthenticationToken.getPassword();
        if (!passwords.contains(password)) {
            throw new BadCredentialsException("You are not robot");
        }
        return RobotAuthenticationToken.authenticated();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RobotAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public RobotAuthenticationProvider addPassword(String password) {
        this.passwords.add(password);
        return this;
    }
}
