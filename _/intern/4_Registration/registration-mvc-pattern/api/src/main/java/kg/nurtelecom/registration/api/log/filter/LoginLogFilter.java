package kg.nurtelecom.registration.api.log.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import kg.nurtelecom.registration.api.log.service.SecurityLoggingService;
import kg.nurtelecom.registration.api.util.HttpUtil;
import kg.nurtelecom.registration.common.enums.Action;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class LoginLogFilter extends OncePerRequestFilter {

    private final SecurityLoggingService service;


    public LoginLogFilter(SecurityLoggingService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);

        if (request.getRequestURI().contains("/api/login")) {
            String ipAddress = HttpUtil.getClientIpAddress(request);
            String username = request.getParameter("username");
            if (!username.isBlank()) {
                service.save(Action.LOGIN, username, ipAddress, request.getHeader("User-Agent"), response.getStatus());
            }
        }
    }
}