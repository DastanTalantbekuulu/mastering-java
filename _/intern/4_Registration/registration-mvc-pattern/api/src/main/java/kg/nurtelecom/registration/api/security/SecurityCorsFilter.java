package kg.nurtelecom.registration.api.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityCorsFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, session_uuid, Accept, x-requested-with, Cache-Control, Content-Disposition, X-FILENAME");
        res.setHeader("Access-Control-Expose-Headers", "Content-Disposition, X-FILENAME");

        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, res);
        }
    }
    @Override
    public void destroy() {
    }
}