package com.coffee.sale.repository.jpa.employee;

import com.coffee.sale.entity.employee.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpa extends JpaRepository<Role, Integer> {
}
