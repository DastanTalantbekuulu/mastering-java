package kg.nurtelecom.registration.api.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import kg.nurtelecom.registration.api.repository.jpa.RefreshTokenRepository;
import kg.nurtelecom.registration.api.security.CustomUserDetails;
import kg.nurtelecom.registration.common.entity.RefreshToken;
import kg.nurtelecom.registration.common.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenServiceManager implements TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenServiceManager.class);
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenServiceManager(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateAccessToken(User user, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", customUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(User user, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        return JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public void saveTokensToDatabase(String refreshToken, Date expiresAt, User user) {
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setToken(refreshToken);
        newRefreshToken.setUser(user);
        newRefreshToken.setCreatedAt(new Date());
        newRefreshToken.setExpiresAt(expiresAt);
        refreshTokenRepository.save(newRefreshToken);
    }

    public String extractUsername(String token) {
        try {
            String secret = "secret";
            byte[] secretKeyBytes = new byte[32];

            System.arraycopy(secret.getBytes(), 0, secretKeyBytes, 0, Math.min(secret.length(), secretKeyBytes.length));

            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "HMACSHA256");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error extracting username from token: {}", e.getMessage(), e);
            throw new RuntimeException("Token parsing failed");
        }
    }


}
