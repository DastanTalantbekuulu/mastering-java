package kg.nurtelecom.registration.api.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.nurtelecom.registration.api.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refresh(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> getAuthenticationInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.getAuthenticationInfo(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
