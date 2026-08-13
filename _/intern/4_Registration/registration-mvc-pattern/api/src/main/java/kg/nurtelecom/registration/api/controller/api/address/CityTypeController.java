package kg.nurtelecom.registration.api.controller.api.address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import kg.nurtelecom.registration.api.service.address.CityTypeService;
import kg.nurtelecom.registration.common.entity.address.CityType;
import kg.nurtelecom.registration.common.payload.request.address.CityTypeRequest;
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
@RequestMapping("/api/directory/city-types")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_REGISTRAR"})
public class CityTypeController {

    private final CityTypeService cityTypeService;

    public CityTypeController(CityTypeService cityTypeService) {
        this.cityTypeService = cityTypeService;
    }

    @GetMapping
    public ResponseEntity<List<CityType>> getAllCityTypes() {
        List<CityType> cityTypeList = cityTypeService.getAllCityTypes();
        return new ResponseEntity<>(cityTypeList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CityType> getCityTypeByID(@PathVariable @Min(1)  Integer id) {
        CityType cityType = cityTypeService.getCityTypeById(id);
        return new ResponseEntity<>(cityType, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CityType> saveCityType(@Valid @RequestBody CityTypeRequest cityTypeRequest) {
        CityType cityType = cityTypeService.saveCityType(cityTypeRequest);
        return new ResponseEntity<>(cityType, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<CityType> updateCityType(@PathVariable @Min(1)  Integer id, @Valid @RequestBody CityTypeRequest cityTypeRequest) {
        CityType cityType = cityTypeService.updateCityType(id, cityTypeRequest);
        return new ResponseEntity<>(cityType, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCityType(@PathVariable @Min(1)  Integer id) {
        cityTypeService.deleteCityTypeByID(id);
        return new ResponseEntity<>("CityType is deleted!",HttpStatus.OK);
    }
}
