package com.coffee.sale.repository;

import com.coffee.sale.entity.coffee.Coffee;
import com.coffee.sale.entity.coffee.CoffeeId;
import com.coffee.sale.entity.coffee.CoffeeInventory;
import com.coffee.sale.entity.coffee.CoffeeInventoryId;
import com.coffee.sale.mapper.CoffeeInventoryMapper;
import com.coffee.sale.payload.request.CoffeeInventoryRequest;
import com.coffee.sale.payload.response.CoffeeInventoryResponse;
import com.coffee.sale.repository.jpa.coffee.CoffeeInventoryJpa;
import com.coffee.sale.repository.jpa.coffee.CoffeeJpa;
import com.coffee.sale.service.CoffeeInventoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CoffeeInventoryRepository implements CoffeeInventoryService {
    private final CoffeeInventoryJpa repository;
    private final CoffeeInventoryMapper mapper;

    private final CoffeeJpa coffeeJpa;

    public CoffeeInventoryRepository(CoffeeInventoryJpa repository, CoffeeInventoryMapper mapper, CoffeeJpa coffeeJpa) {
        this.repository = repository;
        this.mapper = mapper;
        this.coffeeJpa = coffeeJpa;
    }

    public List<CoffeeInventoryResponse> findAll() {
        List<CoffeeInventory> all = repository.findAll();
        List<CoffeeInventoryResponse> response = mapper.toResponse(all);
        return response;
    }

    public List<Integer> getList() {
        List<Integer> distinctIds = repository.findDistinctIds();
        return distinctIds;
    }

    public Page<CoffeeInventoryResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id.coffeeName"));
        Page<CoffeeInventory> coffeeInventories = repository.findAll(pageRequest);
        Page<CoffeeInventoryResponse> responses = coffeeInventories.map(mapper::toResponse);
        return responses;
    }

    public CoffeeInventoryResponse findById(CoffeeInventoryId id) {
        CoffeeInventory coffeeInventory = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coffee Inventory not found"));
        CoffeeInventoryResponse response = mapper.toResponse(coffeeInventory);
        return response;
    }

    public CoffeeInventoryResponse save(CoffeeInventoryRequest request) {
        CoffeeInventoryId coffeeInventoryId = new CoffeeInventoryId(request.coffee(), request.supplierId(), request.warehouseId());
        Optional<CoffeeInventory> byId = repository.findById(coffeeInventoryId);
        CoffeeInventory coffeeInventory;
        if (byId.isEmpty()) {
            coffeeInventory = mapper.toEntity(request);
            Coffee coffee = coffeeJpa.findById(new CoffeeId(request.coffee(), request.supplierId()))
                    .orElseThrow(() -> new EntityNotFoundException("Coffee not found"));
            coffeeInventory.setCoffee(coffee);
        } else {
            coffeeInventory = byId.get();
            coffeeInventory.setQuantity(request.quantity());
        }
        CoffeeInventory saved = repository.save(coffeeInventory);
        CoffeeInventoryResponse response = mapper.toResponse(saved);
        return response;
    }

    public CoffeeInventoryResponse update(CoffeeInventoryRequest request) {
        CoffeeInventoryId id = new CoffeeInventoryId(request.coffee(), request.supplierId(), request.warehouseId());
        CoffeeInventory coffeeInventory = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coffee Inventory not found"));
        coffeeInventory.setQuantity(request.quantity());
        CoffeeInventory saved = repository.save(coffeeInventory);
        CoffeeInventoryResponse response = mapper.toResponse(saved);
        return response;
    }

    public void delete(CoffeeInventoryId id) {
        repository.deleteById(id);
    }
}
