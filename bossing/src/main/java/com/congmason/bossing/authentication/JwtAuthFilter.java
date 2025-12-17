package com.congmason.bossing.authentication;

import com.congmason.bossing.services.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.congmason.bossing.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserService userDetailsService;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(@Lazy UserServiceImpl userDetailsService, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = JwtHelper.extractUsername(token);
            }
//      If the accessToken is null. It will pass the request to next filter in the chain.
//      Any login and signup requests will not have jwt token in their header, therefore they will be passed to next filter chain.
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
//       If any accessToken is present, then it will validate the token and then authenticate the request in security context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (JwtHelper.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            ResponseEntity<?> errorRes = new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
            Exception errorResponse = new Exception(HttpServletResponse.SC_FORBIDDEN + e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //response.getWriter().write(toJson(errorRes));
            //response = (HttpServletResponse) errorRes;
        }

    }
}