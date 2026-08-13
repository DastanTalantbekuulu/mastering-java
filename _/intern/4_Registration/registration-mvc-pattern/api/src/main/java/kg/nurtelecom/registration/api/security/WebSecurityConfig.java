package kg.nurtelecom.registration.api.security;

import kg.nurtelecom.registration.api.log.filter.LoginLogFilter;
import kg.nurtelecom.registration.api.log.filter.LogoutLogFilter;
import kg.nurtelecom.registration.api.repository.jpa.UserRepository;
import kg.nurtelecom.registration.api.security.filter.CustomAuthenticationFilter;
import kg.nurtelecom.registration.api.security.filter.CustomAuthorizationFilter;
import kg.nurtelecom.registration.api.service.auth.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final UserRepository userRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final TokenService tokenService;

    public WebSecurityConfig(UserRepository userRepository, AuthenticationConfiguration authenticationConfiguration, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationConfiguration = authenticationConfiguration;
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginLogFilter loginLogFilter, LogoutLogFilter logoutLogFilter)
            throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationConfiguration, tokenService);
        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter();
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/api/refresh").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(loginLogFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(logoutLogFilter, LogoutFilter.class)
                .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}