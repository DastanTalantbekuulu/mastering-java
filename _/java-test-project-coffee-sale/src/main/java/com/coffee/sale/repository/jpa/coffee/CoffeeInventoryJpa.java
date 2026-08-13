package com.coffee.sale.repository.jpa.coffee;

import com.coffee.sale.entity.coffee.CoffeeInventory;
import com.coffee.sale.entity.coffee.CoffeeInventoryId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoffeeInventoryJpa extends JpaRepository<CoffeeInventory, CoffeeInventoryId> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT warehouse_id FROM cof_inventory")
    List<Integer> findDistinctIds();
}
