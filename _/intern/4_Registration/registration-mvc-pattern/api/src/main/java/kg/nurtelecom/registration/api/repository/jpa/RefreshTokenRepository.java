package kg.nurtelecom.registration.api.repository.jpa;

import kg.nurtelecom.registration.common.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);

    List<RefreshToken> findByUserId(long user_id);
}
