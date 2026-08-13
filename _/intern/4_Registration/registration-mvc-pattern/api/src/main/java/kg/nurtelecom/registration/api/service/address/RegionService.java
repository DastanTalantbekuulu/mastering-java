package kg.nurtelecom.registration.api.service.address;

import kg.nurtelecom.registration.common.entity.address.Region;
import kg.nurtelecom.registration.common.payload.request.address.RegionRequest;

import java.util.List;

public interface RegionService {
    List<Region> getAllRegions();
    Region getRegionByID(Integer id);
    Region saveRegion(RegionRequest regionRequest);
    Region updateRegion(Integer id, RegionRequest regionRequest);
    void deleteRegionByID(Integer id);


}
