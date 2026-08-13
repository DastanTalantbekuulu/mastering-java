package kg.nurtelecom.registration.api.log.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kg.nurtelecom.registration.api.log.service.SecurityLoggingService;
import kg.nurtelecom.registration.api.util.HttpUtil;
import kg.nurtelecom.registration.api.util.SecurityUtil;
import kg.nurtelecom.registration.common.enums.Action;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class LogoutLogFilter extends OncePerRequestFilter {

    private final SecurityLoggingService service;

    public LogoutLogFilter(SecurityLoggingService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().contains("/api/logout") ) {
        String username = SecurityUtil.getUsernameFromJWT(request);
            if(username != null && !username.equals("Unknown")) {
                String ipAddress = HttpUtil.getClientIpAddress(request);
                service.save(Action.LOGOUT, username, ipAddress, request.getHeader("User-Agent"), response.getStatus());
            }
        }
        filterChain.doFilter(request, response);
    }
}
