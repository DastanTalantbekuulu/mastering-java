package com.coffee.sale.security.filter;

import com.coffee.sale.payload.request.AuthRequest;
import com.coffee.sale.payload.response.AuthResponse;
import com.coffee.sale.security.CoffeeUserDetails;
import com.coffee.sale.security.jwt.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CoffeeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public CoffeeAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, ObjectMapper objectMapper) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.setFilterProcessesUrl("/api/login");
    }

    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        AuthRequest cred = getCredentials(request);

        if (cred != null && (cred.username() == null || cred.password() == null)) {
            throw new AuthenticationException("Username or password is missing") {
            };
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(cred.username(), cred.password());
        return authenticationManager.authenticate(auth);
    }

    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) throws IOException {
        CoffeeUserDetails user = (CoffeeUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);
        AuthResponse authResponse = new AuthResponse(token);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }

    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String message = "{\"error\": \"Authentication failed: " + failed.getMessage() + "\"}";
        objectMapper.writeValue(response.getOutputStream(), message);
    }

    private AuthRequest getCredentials(HttpServletRequest request) {
        try {
            String body = request.getReader().lines().collect(Collectors.joining());
            return objectMapper.readValue(body, AuthRequest.class);
        } catch (IOException ioe) {
            return null;
        }
    }
}
