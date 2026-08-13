package kg.nurtelecom.registration.api.service.address;

import kg.nurtelecom.registration.common.payload.request.address.DistrictRequest;
import kg.nurtelecom.registration.common.payload.response.address.DistrictResponse;

import java.util.List;

public interface DistrictService {
    List<DistrictResponse> getAllDistricts();
    DistrictResponse getDistrictByID(Integer id);
    DistrictResponse saveDistrict(DistrictRequest districtRequest);
    DistrictResponse updateDistrict(Integer id, DistrictRequest districtRequest);
    void deleteDistrictByID(Integer id);
}
