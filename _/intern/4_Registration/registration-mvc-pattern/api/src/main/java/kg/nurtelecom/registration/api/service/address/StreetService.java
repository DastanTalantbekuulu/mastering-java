package kg.nurtelecom.registration.api.service.address;

import kg.nurtelecom.registration.common.payload.request.address.StreetRequest;
import kg.nurtelecom.registration.common.payload.response.address.StreetResponse;

import java.util.List;

public interface StreetService {
    List<StreetResponse> getAllStreets();
    StreetResponse getStreetById(Integer id);
    StreetResponse saveStreet(StreetRequest streetRequest);
    StreetResponse updateStreet(Integer id, StreetRequest streetRequest);
    void deleteStreetById(Integer id);
}
