package com.congmason.bossing;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements SecurityFilterChain {
    @Override
    public boolean matches(HttpServletRequest request) {
        return false;
    }

    @Override
    public List<Filter> getFilters() {
        return List.of();
    }
}
