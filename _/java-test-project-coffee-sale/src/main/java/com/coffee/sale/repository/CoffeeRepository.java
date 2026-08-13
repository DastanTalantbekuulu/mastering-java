package com.coffee.sale.repository;

import com.coffee.sale.entity.coffee.Coffee;
import com.coffee.sale.entity.coffee.CoffeeId;
import com.coffee.sale.entity.coffee.Supplier;
import com.coffee.sale.mapper.CoffeeMapper;
import com.coffee.sale.mapper.SupplierMapper;
import com.coffee.sale.payload.request.CoffeeAddRequest;
import com.coffee.sale.payload.request.CoffeeRequest;
import com.coffee.sale.payload.request.CoffeeUpdateRequest;
import com.coffee.sale.payload.response.CoffeeResponse;
import com.coffee.sale.repository.jpa.coffee.CoffeeJpa;
import com.coffee.sale.repository.jpa.coffee.SupplierJpa;
import com.coffee.sale.service.CoffeeService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CoffeeRepository implements CoffeeService {
    private final CoffeeJpa repository;
    private final CoffeeMapper mapper;

    private final SupplierJpa supplierJpa;
    private final SupplierMapper supplierMapper;

    public CoffeeRepository(
            CoffeeJpa repository,
            CoffeeMapper mapper,
            SupplierJpa supplierJpa,
            SupplierMapper supplierMapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.supplierJpa = supplierJpa;
        this.supplierMapper = supplierMapper;
    }

    public List<CoffeeResponse> findAll() {
        List<Coffee> all = repository.findAll();
        List<CoffeeResponse> response = mapper.toResponse(all);
        return response;
    }

    @Override
    public Page<CoffeeResponse> findAll(int page, int size) {
        Page<Coffee> coffeePage = repository.findAll(PageRequest.of(page, size, Sort.by("id.name")));
        Page<CoffeeResponse> response = coffeePage.map(mapper::toResponse);
        return response;
    }

    public CoffeeResponse findById(CoffeeId coffeeId) {
        Coffee coffee = repository.findById(coffeeId).orElseThrow(() -> new EntityNotFoundException("Coffee not found"));
        CoffeeResponse response = mapper.toResponse(coffee);
        return response;
    }

    public CoffeeResponse save(CoffeeRequest request) {
        Supplier supplier = supplierMapper.toEntity(request.supplier());
        Supplier savedSupplier = supplierJpa.save(supplier);

        CoffeeId coffeeId = new CoffeeId(request.name(), savedSupplier.getId());

        Coffee coffee = mapper.toEntity(request);
        coffee.setId(coffeeId);
        coffee.setSupplier(savedSupplier);

        Coffee saved = repository.save(coffee);
        CoffeeResponse response = mapper.toResponse(saved);

        return response;
    }

    @Override
    public CoffeeResponse add(CoffeeAddRequest request) {
        Supplier savedSupplier = supplierJpa.findById(request.supplier())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        CoffeeId coffeeId = new CoffeeId(request.name(), savedSupplier.getId());

        Coffee coffee = mapper.toEntity(request);
        coffee.setId(coffeeId);
        coffee.setSupplier(savedSupplier);

        Coffee saved = repository.save(coffee);
        CoffeeResponse response = mapper.toResponse(saved);

        return response;
    }

    @Override
    public CoffeeResponse update(CoffeeId coffeeId, Integer sales) {
        Coffee coffee = repository.findById(coffeeId)
                .orElseThrow(() -> new EntityNotFoundException("Coffee not found"));
        Integer total = coffee.getSales() + sales;
        coffee.setSales(total);

        Coffee saved = repository.save(coffee);
        CoffeeResponse response = mapper.toResponse(saved);
        return response;
    }

    public CoffeeResponse update(CoffeeId coffeeId, CoffeeUpdateRequest request) {
        Coffee coffee = repository.findById(coffeeId).orElseThrow(() -> new EntityNotFoundException("Coffee not found"));

        coffee.setPrice(request.price());
        coffee.setSales(request.sales());
        coffee.setTotal(request.total());

        Coffee saved = repository.save(coffee);
        CoffeeResponse response = mapper.toResponse(saved);
        return response;
    }

    public void delete(CoffeeId coffeeId) {
        repository.deleteById(coffeeId);
    }

    public boolean exists(CoffeeId coffeeId) {
        return repository.existsById(coffeeId);
    }
}
