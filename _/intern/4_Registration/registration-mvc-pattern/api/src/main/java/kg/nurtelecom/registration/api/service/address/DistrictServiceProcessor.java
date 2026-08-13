package kg.nurtelecom.registration.api.service.address;

import jakarta.persistence.EntityNotFoundException;

import kg.nurtelecom.registration.api.repository.jpa.address.DistrictRepository;
import kg.nurtelecom.registration.common.entity.address.District;
import kg.nurtelecom.registration.common.entity.address.Region;
import kg.nurtelecom.registration.common.payload.request.address.DistrictRequest;
import kg.nurtelecom.registration.common.payload.response.address.DistrictResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictServiceProcessor implements DistrictService {

    private final DistrictRepository districtRepository;
    private final RegionService regionService;

    public DistrictServiceProcessor(DistrictRepository districtRepository, RegionService regionService) {
        this.districtRepository = districtRepository;
        this.regionService = regionService;
    }

    @Override
    public List<DistrictResponse> getAllDistricts() {
        List<District> districtList = districtRepository.findAll();
        List<DistrictResponse> districtResponseList = new ArrayList<>();
        for(District district : districtList) {
            districtResponseList.add(district.toModel());
        }
        return districtResponseList;
    }

    @Override
    public DistrictResponse getDistrictByID(Integer id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Район с ID " + id + " не найден."));
        return district.toModel();
    }

    @Override
    public DistrictResponse saveDistrict(DistrictRequest districtRequest) {
        Region region = regionService.getRegionByID(districtRequest.regionId());
        District district = new District();
        district.setName(districtRequest.name());
        district.setRegion(region);
        district = districtRepository.save(district);
        return district.toModel();
    }

    @Override
    public DistrictResponse updateDistrict(Integer id, DistrictRequest districtRequest) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Район с ID " + id + " не найден."));
        Region region = regionService.getRegionByID(districtRequest.regionId());
        district.setName(districtRequest.name());
        district.setRegion(region);
        district = districtRepository.save(district);
        return district.toModel();
    }

    @Override
    public void deleteDistrictByID(Integer id) {
        if (!districtRepository.existsById(id)) {
            throw new EntityNotFoundException("Район с ID " + id + " не найден.");
        }
        districtRepository.deleteById(id);
    }
}
