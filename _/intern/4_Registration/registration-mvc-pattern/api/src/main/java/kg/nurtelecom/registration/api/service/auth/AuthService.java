package kg.nurtelecom.registration.api.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.nurtelecom.registration.common.entity.RefreshToken;
import kg.nurtelecom.registration.common.entity.User;

import java.io.IOException;

public interface AuthService {
    void logout(HttpServletRequest request);
    void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void getAuthenticationInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
