package com.coffee.sale.mapper;

import com.coffee.sale.entity.coffee.Coffee;
import com.coffee.sale.payload.request.CoffeeAddRequest;
import com.coffee.sale.payload.request.CoffeeRequest;
import com.coffee.sale.payload.response.CoffeeResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CoffeeMapper {
    private final SupplierMapper supplierMapper;

    public CoffeeMapper(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    public Coffee toEntity(CoffeeRequest request) {
        Coffee coffee = new Coffee();
        coffee.setPrice(request.price());
        coffee.setSales(request.sales());
        coffee.setTotal(request.total());
        return coffee;
    }

    public Coffee toEntity(CoffeeAddRequest request) {
        Coffee coffee = new Coffee();
        coffee.setPrice(request.price());
        coffee.setSales(request.sales());
        coffee.setTotal(request.total());
        return coffee;
    }

    public CoffeeResponse toResponse(Coffee coffee) {
        CoffeeResponse response = new CoffeeResponse(
                coffee.getId().name(),
                coffee.getPrice(),
                coffee.getSales(),
                coffee.getTotal(),
                supplierMapper.toResponse(coffee.getSupplier())
        );
        return response;
    }

    public List<CoffeeResponse> toResponse(List<Coffee> coffees) {
        return coffees.stream().map(this::toResponse).toList();
    }
}
