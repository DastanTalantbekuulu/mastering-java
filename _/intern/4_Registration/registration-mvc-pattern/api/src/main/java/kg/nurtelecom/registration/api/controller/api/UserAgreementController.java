package kg.nurtelecom.registration.api.controller.api;

import jakarta.validation.constraints.Min;
import kg.nurtelecom.registration.api.service.agreement.UserAgreementService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user/agreement")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_REGISTRAR"})
public class UserAgreementController {
    private final UserAgreementService userAgreementService;
    private final HttpHeaders headers;

    public UserAgreementController(UserAgreementService userAgreementService) {
        this.userAgreementService = userAgreementService;
        headers = new HttpHeaders();
    }

    @GetMapping("/{personId}")
    public ResponseEntity<byte[]> downloadAgreement(@PathVariable(name = "personId", required = false) @Min(0) Long personId) {
        return ResponseEntity.ok()
                .headers(headers)
                .body(userAgreementService.getUserAgreement(personId));
    }
}
