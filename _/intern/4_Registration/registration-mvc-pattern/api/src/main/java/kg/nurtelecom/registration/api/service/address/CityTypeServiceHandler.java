package kg.nurtelecom.registration.api.service.address;

import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.api.repository.jpa.address.CityTypeRepository;
import kg.nurtelecom.registration.common.entity.address.CityType;
import kg.nurtelecom.registration.common.payload.request.address.CityTypeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityTypeServiceHandler implements CityTypeService {
    private final CityTypeRepository cityTypeRepository;

    public CityTypeServiceHandler(CityTypeRepository cityTypeRepository) {
        this.cityTypeRepository = cityTypeRepository;
    }

    @Override
    public List<CityType> getAllCityTypes() {
        return cityTypeRepository.findAll();
    }

    @Override
    public CityType getCityTypeById(Integer id) {
        return cityTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тип населеннго пункта с ID " + id + " не найден."));
    }

    @Override
    public CityType saveCityType(CityTypeRequest cityTypeRequest) {
        CityType cityType = new CityType();
        cityType.setName(cityTypeRequest.name());
        cityType.setRequiresRegion(cityTypeRequest.requiresRegion());
        cityType.setRequiresDistrict(cityTypeRequest.requiresDistrict());
        return cityTypeRepository.save(cityType);
    }

    @Override
    public CityType updateCityType(Integer id, CityTypeRequest cityTypeRequest) {
        CityType cityType = getCityTypeById(id);
        cityType.setName(cityTypeRequest.name());
        cityType.setRequiresRegion(cityTypeRequest.requiresRegion());
        cityType.setRequiresDistrict(cityTypeRequest.requiresDistrict());
        return cityTypeRepository.save(cityType);
    }

    @Override
    public void deleteCityTypeByID(Integer id) {
        if (!cityTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("Тип населеннго пункта с ID " + id + " не найден.");
        }
        cityTypeRepository.deleteById(id);
    }
}
