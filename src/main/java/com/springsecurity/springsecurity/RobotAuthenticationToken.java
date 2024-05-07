package com.springsecurity.springsecurity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RobotAuthenticationToken extends AbstractAuthenticationToken {
    private final String password;

    private RobotAuthenticationToken(String password, List<GrantedAuthority> authorities) {
        super(authorities);
        this.password = password;
        this.setAuthenticated(password == null);
    }

    public static RobotAuthenticationToken authenticated() {
       return new RobotAuthenticationToken(null, AuthorityUtils.createAuthorityList("ROLE_robot"));
    }

    public static RobotAuthenticationToken unauthenticated(String password) {
        return new RobotAuthenticationToken(password, Collections.emptyList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "Mr robot";
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
    
    public String getPassword() {
        return password;
    }
}
