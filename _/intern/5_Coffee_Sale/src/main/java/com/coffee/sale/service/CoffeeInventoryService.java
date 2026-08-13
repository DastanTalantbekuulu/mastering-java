package com.coffee.sale.service;

import com.coffee.sale.entity.coffee.CoffeeInventoryId;
import com.coffee.sale.payload.request.CoffeeInventoryRequest;
import com.coffee.sale.payload.response.CoffeeInventoryResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CoffeeInventoryService {
    List<CoffeeInventoryResponse> findAll();

    List<Integer> getList();

    Page<CoffeeInventoryResponse> findAll(int page, int size);

    CoffeeInventoryResponse findById(CoffeeInventoryId id);

    @PreAuthorize("hasAuthority('ADMIN')")
    CoffeeInventoryResponse save(CoffeeInventoryRequest request);

    @PreAuthorize("hasAuthority('ADMIN')")
    CoffeeInventoryResponse update(CoffeeInventoryRequest request);

    @PreAuthorize("hasAuthority('OWNER')")
    void delete(CoffeeInventoryId id);
}
