package kg.nurtelecom.registration.api.service.address;

import kg.nurtelecom.registration.common.entity.address.CityType;
import kg.nurtelecom.registration.common.payload.request.address.CityTypeRequest;

import java.util.List;

public interface CityTypeService {
    List<CityType> getAllCityTypes();
    CityType getCityTypeById(Integer id);
    CityType saveCityType(CityTypeRequest cityTypeRequest);
    CityType updateCityType(Integer id, CityTypeRequest cityTypeRequest);
    void deleteCityTypeByID(Integer id);
}
