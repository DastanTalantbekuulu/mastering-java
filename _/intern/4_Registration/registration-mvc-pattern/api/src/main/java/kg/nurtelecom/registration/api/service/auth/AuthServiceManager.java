package kg.nurtelecom.registration.api.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.nurtelecom.registration.api.repository.jpa.RefreshTokenRepository;
import kg.nurtelecom.registration.api.repository.jpa.UserRepository;
import kg.nurtelecom.registration.api.security.CustomUserDetails;
import kg.nurtelecom.registration.common.entity.RefreshToken;
import kg.nurtelecom.registration.common.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
public class AuthServiceManager implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceManager.class);
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthServiceManager(UserRepository userRepository, TokenService tokenService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void logout(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String accessToken = authorizationHeader.substring("Bearer ".length());

                String username = tokenService.extractUsername(accessToken);

                User user = userRepository.findByUserName(username);

                if (user != null) {
                    List<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(user.getId());
                    if (!refreshToken.isEmpty()) {
                        refreshTokenRepository.deleteAll(refreshToken);
                    }
                }
            }
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            RefreshToken storedToken;

            try {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String username = decodedJWT.getSubject();
                User user = userRepository.findByUserName(username);

                if (user == null) {
                    throw new RuntimeException("User not found");
                }

                storedToken = refreshTokenRepository.findByToken(refreshToken);
                if (storedToken == null) {
                    throw new RuntimeException("Invalid refresh token");
                }

                String accessToken = tokenService.generateAccessToken(user, request);
                String newRefreshToken = tokenService.generateRefreshToken(user, request);

                tokenService.saveTokensToDatabase(newRefreshToken, storedToken.getExpiresAt(), user);

                refreshTokenRepository.delete(storedToken);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", newRefreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                storedToken = refreshTokenRepository.findByToken(refreshToken);
                if (storedToken != null) {
                    refreshTokenRepository.delete(storedToken);
                }
                error.put("error_message", "Время сессии истекло, зайдите в систему заново.");

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Authorization header is missing");
        }
    }

    public void getAuthenticationInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);

                String username = decodedJWT.getSubject();
                User user = userRepository.findByUserName(username);

                CustomUserDetails customUserDetails = new CustomUserDetails(user);

                Map<String, Object> authenticationInfo = new HashMap<>();
                authenticationInfo.put("username", user.getUserName());
                authenticationInfo.put("role", customUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                authenticationInfo.put("token", token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationInfo);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", "Ошибка получения информации об аутентификации.");

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Authorization header is missing");
        }
    }
}
