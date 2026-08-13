package com.coffee.sale.repository.jpa.employee;

import com.coffee.sale.entity.employee.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpa extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
