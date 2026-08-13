package kg.nurtelecom.registration.api.security;

import kg.nurtelecom.registration.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User users;

    public CustomUserDetails(User users) {
        if (users == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.users = users;
    }

    public User getId() {
        return users;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + users.getRole().getName()));
        return authorities;
    }

    public String getPassword() {
        return users.getPassword();
    }

    public String getUsername() {
        return users.getUserName();
    }

    @Override
    public boolean isEnabled() {
        return users.isActivated();
    }
}
