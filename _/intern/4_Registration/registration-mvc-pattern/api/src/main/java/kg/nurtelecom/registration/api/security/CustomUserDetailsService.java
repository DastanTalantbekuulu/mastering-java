package kg.nurtelecom.registration.api.security;

import kg.nurtelecom.registration.api.repository.jpa.UserRepository;
import kg.nurtelecom.registration.common.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = userRepository.findByUserName(username);
        if (users == null) {
            throw new UsernameNotFoundException("Пользователь не найден: " + username);
        }
        return new CustomUserDetails(users);
    }
}

