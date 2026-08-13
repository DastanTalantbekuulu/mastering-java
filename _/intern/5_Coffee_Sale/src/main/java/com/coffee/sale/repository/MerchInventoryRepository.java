package com.coffee.sale.repository;

import com.coffee.sale.entity.coffee.MerchInventory;
import com.coffee.sale.entity.coffee.Supplier;
import com.coffee.sale.mapper.MerchInventoryMapper;
import com.coffee.sale.payload.request.MerchInventoryRequest;
import com.coffee.sale.payload.request.MerchInventoryUpdateRequest;
import com.coffee.sale.payload.response.MerchInventoryResponse;
import com.coffee.sale.repository.jpa.coffee.MerchInventoryJpa;
import com.coffee.sale.repository.jpa.coffee.SupplierJpa;
import com.coffee.sale.service.MerchInventoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class MerchInventoryRepository implements MerchInventoryService {
    private final MerchInventoryJpa repository;
    private final MerchInventoryMapper mapper;

    private final SupplierJpa supplierJpa;

    public MerchInventoryRepository(MerchInventoryJpa repository, MerchInventoryMapper mapper, SupplierJpa supplierJpa) {
        this.repository = repository;
        this.mapper = mapper;
        this.supplierJpa = supplierJpa;
    }

    public List<MerchInventoryResponse> findAll() {
        List<MerchInventory> all = repository.findAll();
        List<MerchInventoryResponse> response = mapper.toResponse(all);
        return response;
    }

    public Page<MerchInventoryResponse> findAll(int page, int size) {
        Page<MerchInventory> merchInventories = repository.findAll(PageRequest.of(page, size, Sort.by("name")));
        Page<MerchInventoryResponse> response = merchInventories.map(mapper::toResponse);
        return response;
    }

    public MerchInventoryResponse findById(Integer id) {
        MerchInventory merchInventory = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Merch Inventory not found"));
        MerchInventoryResponse response = mapper.toResponse(merchInventory);
        return response;
    }

    public MerchInventoryResponse save(MerchInventoryRequest request) {
        Supplier supplier = supplierJpa.findById(request.supplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        MerchInventory merchInventory = mapper.toEntity(request);
        merchInventory.setSupplier(supplier);
        MerchInventory saved = repository.save(merchInventory);
        MerchInventoryResponse response = mapper.toResponse(saved);
        return response;
    }

    public MerchInventoryResponse update(Integer id, MerchInventoryUpdateRequest request) {
        MerchInventory merchInventory = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Merch Inventory not found"));
        merchInventory.setQuantity(request.quantity());
        merchInventory.setName(request.name());
        MerchInventory saved = repository.save(merchInventory);
        MerchInventoryResponse response = mapper.toResponse(saved);
        return response;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
