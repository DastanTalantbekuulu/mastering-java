package com.coffee.sale.mapper;

import com.coffee.sale.entity.coffee.MerchInventory;
import com.coffee.sale.payload.request.MerchInventoryRequest;
import com.coffee.sale.payload.response.MerchInventoryResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MerchInventoryMapper {
    public MerchInventory toEntity(MerchInventoryRequest request) {
        MerchInventory entity = new MerchInventory();
        entity.setId(request.id());
        entity.setName(request.name());
        entity.setQuantity(request.quantity());
        return entity;
    }

    public MerchInventoryResponse toResponse(MerchInventory entity) {
        MerchInventoryResponse response = new MerchInventoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getSupplier().getId(),
                entity.getQuantity(),
                entity.getUpdatedAt()
        );
        return response;
    }

    public List<MerchInventoryResponse> toResponse(List<MerchInventory> entities) {
        return entities.stream().map(this::toResponse).toList();
    }
}
