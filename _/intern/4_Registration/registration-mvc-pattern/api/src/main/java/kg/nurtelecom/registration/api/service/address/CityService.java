package kg.nurtelecom.registration.api.service.address;

import kg.nurtelecom.registration.common.payload.request.address.CityRequest;
import kg.nurtelecom.registration.common.payload.response.address.CityResponse;

import java.util.List;

public interface CityService {
    List<CityResponse> getAllCities();
    CityResponse getCityByID(Integer id);
    CityResponse saveCity(CityRequest cityRequest);
    CityResponse updateCity(Integer id, CityRequest cityRequest);
    void deleteCityByID(Integer id);
}
