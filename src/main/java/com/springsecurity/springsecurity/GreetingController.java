package com.springsecurity.springsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/public")
    public String publicPages() {
        return "public";
    }

    @GetMapping("/")
    public String basePage() {
        return "This is root";
    }


    @GetMapping("/private")
    public String privatePage(Authentication authentication) {
        System.out.println("coming here!");
        return "Welcome to this very private page, ~[" + getName(authentication) + "]~!";
    }

    private static String getName(Authentication authentication) {
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            return oidcUser.getEmail();
        }
        return authentication.getName();
    }
}
