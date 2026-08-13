package kg.nurtelecom.registration.api.repository.jpa;

import kg.nurtelecom.registration.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
