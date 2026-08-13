package com.coffee.sale.mapper;

import com.coffee.sale.entity.coffee.CoffeeHouse;
import com.coffee.sale.payload.request.CoffeeHouseRequest;
import com.coffee.sale.payload.response.CoffeeHouseResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CoffeeHouseMapper {
    public CoffeeHouse toEntity(CoffeeHouseRequest request) {
        CoffeeHouse coffeeHouse = new CoffeeHouse();
        coffeeHouse.setStoreId(request.storeId());
        coffeeHouse.setCity(request.city());
        coffeeHouse.setCoffee(request.coffee());
        coffeeHouse.setMerch(request.merch());
        coffeeHouse.setTotal(request.merch() + request.coffee());
        return coffeeHouse;
    }

    public CoffeeHouseResponse toResponse(CoffeeHouse coffeeHouse) {
        CoffeeHouseResponse response = new CoffeeHouseResponse(
                coffeeHouse.getStoreId(),
                coffeeHouse.getCity(),
                coffeeHouse.getCoffee(),
                coffeeHouse.getMerch(),
                coffeeHouse.getTotal()
        );
        return response;
    }

    public List<CoffeeHouseResponse> toResponse(List<CoffeeHouse> coffeeHouses) {
        return coffeeHouses.stream().map(this::toResponse).toList();
    }
}
