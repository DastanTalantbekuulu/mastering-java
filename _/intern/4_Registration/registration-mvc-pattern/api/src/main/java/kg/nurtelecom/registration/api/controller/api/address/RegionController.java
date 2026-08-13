package kg.nurtelecom.registration.api.controller.api.address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import kg.nurtelecom.registration.api.service.address.RegionService;
import kg.nurtelecom.registration.common.entity.address.Region;
import kg.nurtelecom.registration.common.payload.request.address.RegionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/directory/regions")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_REGISTRAR"})
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> regionList;
        regionList = regionService.getAllRegions();
        return new ResponseEntity<>(regionList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Region> getRegionByID(@PathVariable @Min(1) Integer id) {
        Region region = regionService.getRegionByID(id);
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Region> saveRegion(@Valid @RequestBody RegionRequest regionRequest) {
        Region region = regionService.saveRegion(regionRequest);
        return new ResponseEntity<>(region, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable @Min(1) Integer id, @Valid @RequestBody RegionRequest regionRequest) {
        Region region = regionService.updateRegion(id, regionRequest);
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteRegion(@PathVariable @Min(1) Integer id) {
        regionService.deleteRegionByID(id);
        return new ResponseEntity<>("Region is deleted!", HttpStatus.OK);
    }
}
