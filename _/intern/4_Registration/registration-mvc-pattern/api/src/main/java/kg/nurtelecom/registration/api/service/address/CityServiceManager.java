package kg.nurtelecom.registration.api.service.address;

import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.api.repository.jpa.address.CityRepository;
import kg.nurtelecom.registration.api.repository.jpa.address.CityTypeRepository;
import kg.nurtelecom.registration.api.repository.jpa.address.DistrictRepository;
import kg.nurtelecom.registration.api.repository.jpa.address.RegionRepository;
import kg.nurtelecom.registration.common.entity.address.City;
import kg.nurtelecom.registration.common.entity.address.CityType;
import kg.nurtelecom.registration.common.entity.address.District;
import kg.nurtelecom.registration.common.entity.address.Region;
import kg.nurtelecom.registration.common.payload.request.address.CityRequest;
import kg.nurtelecom.registration.common.payload.response.address.CityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceManager implements CityService {

    private final CityRepository cityRepository;
    private final CityTypeRepository cityTypeRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    public CityServiceManager(CityRepository cityRepository, CityTypeRepository cityTypeRepository, RegionRepository regionRepository, DistrictRepository districtRepository) {
        this.cityRepository = cityRepository;
        this.cityTypeRepository = cityTypeRepository;
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
    }

    @Override
    public List<CityResponse> getAllCities() {
        List<City> cityList = cityRepository.findAll();
        List<CityResponse> cityResponseList = new ArrayList<>();
        for(City city : cityList) {
            cityResponseList.add(city.toModel());
        }
        return cityResponseList;
    }

    @Override
    public CityResponse getCityByID(Integer id) {
        City city = findEntityByIdOrThrow(cityRepository, id, "Город");
        return city.toModel();
    }

    @Override
    public CityResponse saveCity(CityRequest cityRequest) {
        CityType cityType = findEntityByIdOrThrow(cityTypeRepository, cityRequest.cityTypeId(), "Тип населенного пункта");

        Region region = null;
        District district = null;

        if (cityType.isRequiresRegion()) {
            region = findEntityByIdOrThrow(regionRepository, cityRequest.regionId(), "Область");
            if (cityType.isRequiresDistrict()) {
                district = findEntityByIdOrThrow(districtRepository, cityRequest.districtId(), "Район");
            }
        }

        City city = new City();
        city.setName(cityRequest.name());
        city.setCityType(cityType);
        city.setRegion(region);
        city.setDistrict(district);
        city = cityRepository.save(city);
        return city.toModel();
    }

    @Override
    public CityResponse updateCity(Integer id, CityRequest cityRequest) {
        City city = findEntityByIdOrThrow(cityRepository, id, "Город");

        CityType cityType = findEntityByIdOrThrow(cityTypeRepository, cityRequest.cityTypeId(), "Тип населенного пункта");

        Region region = null;
        District district = null;

        if (cityType.isRequiresRegion()) {
            region = findEntityByIdOrThrow(regionRepository, cityRequest.regionId(), "Область");
            if (cityType.isRequiresDistrict()) {
                district = findEntityByIdOrThrow(districtRepository, cityRequest.districtId(), "Район");
            }
        }

        city.setName(cityRequest.name());
        city.setCityType(cityType);
        city.setRegion(region);
        city.setDistrict(district);
        city = cityRepository.save(city);

        return city.toModel();
    }

    @Override
    public void deleteCityByID(Integer id) {
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException("Город с ID " + id + " не найден.");
        }
        cityRepository.deleteById(id);
    }

    private <T> T findEntityByIdOrThrow(JpaRepository<T, Integer> repository, Integer id, String entityName) {
        if (id == null) {
            throw new IllegalArgumentException("ID для " + entityName + " не может быть null");
        }
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " с ID " + id + " не найден."));
    }
}
