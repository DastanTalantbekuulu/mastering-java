package kg.nurtelecom.registration.api.controller.api.address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import kg.nurtelecom.registration.api.service.address.StreetService;
import kg.nurtelecom.registration.common.payload.request.address.StreetRequest;
import kg.nurtelecom.registration.common.payload.response.address.StreetResponse;
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
@RequestMapping("/api/directory/streets")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_REGISTRAR"})
public class StreetController {
    private final StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @GetMapping
    public ResponseEntity<List<StreetResponse>> getAllStreets() {
        List<StreetResponse> streetList = streetService.getAllStreets();
        return new ResponseEntity<>(streetList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StreetResponse> getStreetById(@PathVariable @Min(1) Integer id){
        StreetResponse street = streetService.getStreetById(id);
        return new ResponseEntity<>(street, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StreetResponse> saveStreet(@Valid @RequestBody StreetRequest streetRequest) {
        StreetResponse street = streetService.saveStreet(streetRequest);
        return new ResponseEntity<>(street, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<StreetResponse> updateStreet(@PathVariable @Min(1) Integer id, @Valid @RequestBody StreetRequest streetRequest) {
        StreetResponse street = streetService.updateStreet(id, streetRequest);
        return new ResponseEntity<>(street, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteStreet(@PathVariable @Min(1) Integer id) {
        streetService.deleteStreetById(id);
        return new ResponseEntity<>("Street is deleted!", HttpStatus.CREATED);
    }
}
