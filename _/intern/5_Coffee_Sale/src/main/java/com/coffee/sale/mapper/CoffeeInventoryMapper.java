package com.coffee.sale.mapper;

import com.coffee.sale.entity.coffee.CoffeeInventory;
import com.coffee.sale.entity.coffee.CoffeeInventoryId;
import com.coffee.sale.payload.request.CoffeeInventoryRequest;
import com.coffee.sale.payload.response.CoffeeInventoryResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CoffeeInventoryMapper {
    public CoffeeInventory toEntity(CoffeeInventoryRequest request) {
        CoffeeInventoryId id = new CoffeeInventoryId(request.coffee(), request.supplierId(), request.warehouseId());

        CoffeeInventory coffeeInventory = new CoffeeInventory();
        coffeeInventory.setId(id);
        coffeeInventory.setQuantity(request.quantity());

        return coffeeInventory;
    }

    public CoffeeInventoryResponse toResponse(CoffeeInventory coffeeInventory) {
        CoffeeInventoryResponse response = new CoffeeInventoryResponse(
                coffeeInventory.getId().warehouseId(),
                coffeeInventory.getId().coffeeName(),
                coffeeInventory.getId().supplierId(),
                coffeeInventory.getQuantity(),
                coffeeInventory.getUpdatedAt()
        );
        return response;
    }

    public List<CoffeeInventoryResponse> toResponse(List<CoffeeInventory> coffeeInventories) {
        return coffeeInventories.stream().map(this::toResponse).toList();
    }
}
