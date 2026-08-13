package kg.nurtelecom.registration.api.service.address;

import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.api.repository.jpa.address.RegionRepository;
import kg.nurtelecom.registration.common.entity.address.Region;
import kg.nurtelecom.registration.common.payload.request.address.RegionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceManager implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceManager(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Region getRegionByID(Integer id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Область с ID " + id + " не найдена."));
    }

    @Override
    public Region saveRegion(RegionRequest regionRequest) {
        Region region = new Region();
        region.setName(regionRequest.name());
        return regionRepository.save(region);
    }

    @Override
    public Region updateRegion(Integer id, RegionRequest regionRequest) {
        Region region = getRegionByID(id);
        region.setName(regionRequest.name());
        return regionRepository.save(region);
    }

    @Override
    public void deleteRegionByID(Integer id) {
        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException("Область с ID " + id + " не найдена.");
        }
        regionRepository.deleteById(id);
    }
}
