package com.coffee.sale.mapper;

import com.coffee.sale.entity.coffee.Supplier;
import com.coffee.sale.payload.request.SupplierRequest;
import com.coffee.sale.payload.response.SupplierResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupplierMapper {
    public Supplier toEntity(SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.name());
        supplier.setStreet(request.street());
        supplier.setCity(request.city());
        supplier.setState(request.state());
        supplier.setZip(request.zip());
        return supplier;
    }

    public SupplierResponse toResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getStreet(),
                supplier.getCity(),
                supplier.getState(),
                supplier.getZip()
        );
        return response;
    }

    public List<SupplierResponse> toResponse(List<Supplier> suppliers) {
        return suppliers.stream().map(this::toResponse).toList();
    }
}
