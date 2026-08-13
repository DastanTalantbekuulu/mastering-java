package kg.nurtelecom.registration.api.controller.api.address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import kg.nurtelecom.registration.api.service.address.DistrictService;
import kg.nurtelecom.registration.common.payload.request.address.DistrictRequest;
import kg.nurtelecom.registration.common.payload.response.address.DistrictResponse;
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
@RequestMapping("api/directory/districts")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_REGISTRAR"})
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping
    public ResponseEntity<List<DistrictResponse>> getAllDistricts() {
        List<DistrictResponse> districtList = districtService.getAllDistricts();
        return new ResponseEntity<>(districtList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DistrictResponse> getDistrictByID(@PathVariable @Min(1) Integer id) {
        DistrictResponse district = districtService.getDistrictByID(id);
        return new ResponseEntity<>(district, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DistrictResponse> saveDistrict(@Valid @RequestBody DistrictRequest districtRequest){
        DistrictResponse district = districtService.saveDistrict(districtRequest);
        return new ResponseEntity<>(district, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<DistrictResponse> updateDistrict(@PathVariable @Min(1) Integer id, @Valid @RequestBody DistrictRequest districtRequest) {
        DistrictResponse district = districtService.updateDistrict(id, districtRequest);
        return new ResponseEntity<>(district, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteDistrict(@PathVariable @Min(1) Integer id) {
        districtService.deleteDistrictByID(id);
        return new ResponseEntity<>("District is deleted!",HttpStatus.OK);
    }
}
