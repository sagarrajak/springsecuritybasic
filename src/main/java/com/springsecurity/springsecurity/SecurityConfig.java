package com.springsecurity.springsecurity;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEventPublisher publisher
    ) throws Exception {
        ProviderManager authManager = new ProviderManager(new RobotAuthenticationProvider().addPassword("test-123").addPassword("123-test"));
        authManager.setAuthenticationEventPublisher(publisher);
        return http.authorizeHttpRequests(autozierHttp -> {
            autozierHttp.requestMatchers("/").permitAll();
            autozierHttp.requestMatchers("/public").permitAll();
            autozierHttp.requestMatchers("/favicon.svg").permitAll();
            autozierHttp.requestMatchers("/css/*").permitAll();
            autozierHttp.requestMatchers("/error").permitAll();
            autozierHttp.anyRequest().authenticated();
        }).formLogin(Customizer.withDefaults())
        .oauth2Login(Customizer.withDefaults())
        .addFilterBefore(new SagarFIlter(), AuthorizationFilter.class)
        .addFilterBefore(new RobotAuthenticationFilter(authManager), AuthorizationFilter.class)
        .authenticationProvider(new SagarAuthenticationProvider())
        .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("sagar")
                        .password("{noop}password")
                        .authorities("Role_user")
                        .build()
        );
    }


    @Bean
    public ApplicationListener<AuthenticationSuccessEvent>  successListener() {
        return  event -> {
            System.out.println(String.format(" SUCCESS [%s] %s", event.getAuthentication().getClass().getName(), event.getAuthentication().getName()));
        };
    }
}
