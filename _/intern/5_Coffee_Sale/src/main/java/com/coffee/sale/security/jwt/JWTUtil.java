package com.coffee.sale.security.jwt;

import com.coffee.sale.security.CoffeeUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JWTUtil {

    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    private static final Duration EXP = Duration.ofDays(1);
    private static final byte DOT = (byte) '.';
    private static final Map<String, Object> HEADER = Map.of("alg", "HS256", "typ", "JWT");

    private final JWTSigner signer;
    private final ObjectMapper objectMapper;
    private final ObjectWriter objectWriter;

    public JWTUtil(@Value("${jwt.secret}") String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        signer = new JWTSigner(secret);
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writerFor(Map.class);
    }

    public String generateToken(CoffeeUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put("sub", userDetails.getUsername());
        claims.put("iat", System.currentTimeMillis());
        claims.put("exp", System.currentTimeMillis() + EXP.toMillis());
        claims.put("role", userDetails.getRoles());

        try {
            String payload = objectWriter.writeValueAsString(claims);
            return create(payload);
        } catch (JsonProcessingException e) {
            log.error("Failed to generate JWT for user: {}", userDetails.getUsername(), e);
            throw new JWTException("Failed to generate JWT", e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String[] parts = splitJWT(token);
            if (!verify(parts)) {
                return false;
            }
            JsonNode jsonNode = objectMapper.readTree(parsePayload(token));

            String username = jsonNode.path("sub").asText();
            long exp = jsonNode.path("exp").asLong();
            return username.equals(userDetails.getUsername()) && exp > System.currentTimeMillis();
        } catch (Exception e) {
            log.error("Failed to validate JWT for user: {}", userDetails.getUsername(), e);
            return false;
        }
    }

    private boolean verify(String[] parts) {
        try {
            byte[] data = concatenate(parts[0].getBytes(UTF_8), parts[1].getBytes(UTF_8));
            byte[] expectedSignature = JWTCoder.decode(parts[2]);

            return signer.verify(data, expectedSignature);
        } catch (Exception e) {
            log.error("JWT verification failed");
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            return objectMapper.readTree(parsePayload(token)).path("sub").asText();
        } catch (JsonProcessingException e) {
            log.error("Failed to extract username from JWT", e);
            throw new JWTException("Failed to extract username from JWT", e);
        }
    }

    private String create(String payload) {
        try {
            byte[] encodedHeader = JWTCoder.encode(objectWriter.writeValueAsBytes(HEADER));
            byte[] encodedPayload = JWTCoder.encode(payload.getBytes(UTF_8));

            byte[] data = concatenate(encodedHeader, encodedPayload);
            byte[] signature = signer.sign(data);
            byte[] encodedSignature = JWTCoder.encode(signature);

            return new String(data, UTF_8) + "." + new String(encodedSignature, UTF_8);
        } catch (JsonProcessingException e) {
            log.error("Failed to encode JWT");
            throw new JWTException("Failed to encode JWT", e);
        }
    }

    private static byte[] concatenate(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length + 1];
        System.arraycopy(first, 0, result, 0, first.length);
        result[first.length] = DOT;
        System.arraycopy(second, 0, result, first.length + 1, second.length);
        return result;
    }

    private String parsePayload(String jwt) {
        try {
            return new String(JWTCoder.decode(splitJWT(jwt)[1]), UTF_8);
        } catch (Exception e) {
            log.error("Failed to parse JWT payload", e);
            throw new JWTException("Failed to parse JWT payload", e);
        }
    }

    private String[] splitJWT(String jwt) {
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            throw new JWTException("Invalid JWT format");
        }
        return parts;
    }

    public String[] headerDecode64(String authentication) {
        String basic = authentication.substring("Basic ".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(basic);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2);
    }
}
