package com.coffee.sale.security;

import com.coffee.sale.entity.employee.Role;
import com.coffee.sale.entity.employee.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CoffeeUserDetails implements UserDetails {
    private final User user;

    public CoffeeUserDetails(User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public List<String> getRoles() {
        return user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isEnabled() {
        return user.getEnabled();
    }
}
