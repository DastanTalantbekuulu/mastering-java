package com.coffee.sale.security;

import com.coffee.sale.entity.employee.User;
import com.coffee.sale.repository.jpa.employee.UserJpa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CoffeeUserDetailsService implements UserDetailsService {
    private final UserJpa repository;

    public CoffeeUserDetailsService(UserJpa repository) {
        this.repository = repository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CoffeeUserDetails(users);
    }
}
