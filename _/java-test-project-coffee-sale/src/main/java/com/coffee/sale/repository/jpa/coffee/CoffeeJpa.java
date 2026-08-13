package com.coffee.sale.repository.jpa.coffee;

import com.coffee.sale.entity.coffee.Coffee;
import com.coffee.sale.entity.coffee.CoffeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeJpa extends JpaRepository<Coffee, CoffeeId> {
}
