package com.coffee.sale.repository.jpa.coffee;

import com.coffee.sale.entity.coffee.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierJpa extends JpaRepository<Supplier, Integer> {
}
