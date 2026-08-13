package kg.nurtelecom.registration.api.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.registration.common.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface TokenService {
    void saveTokensToDatabase(String refreshToken, Date expiresAt, User user);

    String generateRefreshToken(User user, HttpServletRequest request);

    String generateAccessToken(User user, HttpServletRequest request);

    String extractUsername(String token);
}
