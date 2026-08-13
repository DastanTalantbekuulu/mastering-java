package kg.nurtelecom.registration.api.repository.jpa.address;

import kg.nurtelecom.registration.common.entity.address.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
