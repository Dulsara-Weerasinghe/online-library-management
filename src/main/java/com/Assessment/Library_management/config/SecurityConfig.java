package com.Assessment.Library_management.config;

import com.Assessment.Library_management.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {



    private final JwtFilter JwtFilter;

    @Autowired
    public SecurityConfig(com.Assessment.Library_management.filter.JwtFilter jwtFilter) {
        JwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/authenticate").permitAll() //Allow anyone (without login) to access the /auth/authenticate API (like  login endpoint).
                        .requestMatchers("/api/v1/books/availableBooks").authenticated() //check availble books API dont need to authenticate , anyone can view
                        .anyRequest().authenticated() //All other APIs (except /auth/authenticate) must have a valid JWT token.
                )
                .addFilterBefore(JwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();


    }


}
