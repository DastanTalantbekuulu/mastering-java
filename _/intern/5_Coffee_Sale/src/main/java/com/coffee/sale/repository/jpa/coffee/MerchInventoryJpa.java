package com.coffee.sale.repository.jpa.coffee;

import com.coffee.sale.entity.coffee.MerchInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchInventoryJpa extends JpaRepository<MerchInventory, Integer> {
}
