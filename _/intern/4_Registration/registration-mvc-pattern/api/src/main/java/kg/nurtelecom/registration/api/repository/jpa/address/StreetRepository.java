package kg.nurtelecom.registration.api.repository.jpa.address;

import kg.nurtelecom.registration.common.entity.address.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Integer> {
}
