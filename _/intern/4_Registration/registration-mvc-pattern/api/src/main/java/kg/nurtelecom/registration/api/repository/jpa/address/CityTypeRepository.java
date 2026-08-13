package kg.nurtelecom.registration.api.repository.jpa.address;

import kg.nurtelecom.registration.common.entity.address.CityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityTypeRepository extends JpaRepository<CityType, Integer> {
}
