package kg.nurtelecom.registration.api.controller.api.address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import kg.nurtelecom.registration.api.service.address.CityService;
import kg.nurtelecom.registration.common.payload.request.address.CityRequest;
import kg.nurtelecom.registration.common.payload.response.address.CityResponse;
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
@RequestMapping("/api/directory/cities")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_REGISTRAR"})
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        List<CityResponse> cityList = cityService.getAllCities();
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CityResponse> getCityByID(@PathVariable @Min(1) Integer id) {
        CityResponse city = cityService.getCityByID(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CityResponse> saveCity(@Valid @RequestBody CityRequest cityRequest) {
        CityResponse city = cityService.saveCity(cityRequest);
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable @Min(1) Integer id, @Valid @RequestBody CityRequest cityRequest) {
        CityResponse city = cityService.updateCity(id, cityRequest);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable @Min(1) Integer id) {
        cityService.deleteCityByID(id);
        return new ResponseEntity<>("City is deleted!",HttpStatus.OK);
    }
}
