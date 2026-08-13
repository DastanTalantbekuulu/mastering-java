package com.coffee.sale.repository.jpa.coffee;

import com.coffee.sale.entity.coffee.CoffeeHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeHouseJpa extends JpaRepository<CoffeeHouse, Integer> {
}
