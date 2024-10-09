package com.api.Library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admins/**").hasRole("ADMIN")  // Protect admin routes
                                .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN") // Allow access to users and admins
                                .anyRequest().authenticated() // Require authentication for all other requests
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .authorizationEndpoint(authorization ->
                                        authorization.baseUri("/oauth2/authorization") // Base URI for OAuth2 authorization
                                )
                                .redirectionEndpoint(redirection ->
                                        redirection.baseUri("/oauth2/callback/*") // Base URI for OAuth2 redirection
                                )
                )
                .logout(logout -> logout.permitAll()); // Allow everyone to access logout

        return http.build();
    }

}