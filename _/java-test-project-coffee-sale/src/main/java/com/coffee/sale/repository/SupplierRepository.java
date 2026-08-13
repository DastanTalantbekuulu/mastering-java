package com.coffee.sale.repository;

import com.coffee.sale.entity.coffee.Supplier;
import com.coffee.sale.mapper.SupplierMapper;
import com.coffee.sale.payload.request.SupplierRequest;
import com.coffee.sale.payload.response.SupplierResponse;
import com.coffee.sale.repository.jpa.coffee.SupplierJpa;
import com.coffee.sale.service.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierRepository implements SupplierService {
    private final SupplierJpa repository;
    private final SupplierMapper mapper;

    public SupplierRepository(SupplierJpa repository, SupplierMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SupplierResponse> findAll() {
        List<Supplier> all = repository.findAll();
        List<SupplierResponse> response = mapper.toResponse(all);
        return response;
    }

    public Page<SupplierResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        Page<Supplier> suppliers = repository.findAll(pageRequest);
        Page<SupplierResponse> supplierResponses = suppliers.map(mapper::toResponse);
        return supplierResponses;
    }

    public SupplierResponse findById(Integer id) {
        Supplier supplier = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        SupplierResponse response = mapper.toResponse(supplier);
        return response;
    }

    public SupplierResponse save(SupplierRequest request) {
        Supplier supplier = mapper.toEntity(request);
        Supplier saved = repository.save(supplier);
        SupplierResponse response = mapper.toResponse(saved);
        return response;
    }

    public SupplierResponse update(Integer id, SupplierRequest request) {
        Supplier supplier = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        supplier.setName(request.name());
        supplier.setStreet(request.street());
        supplier.setCity(request.city());
        supplier.setState(request.state());
        supplier.setZip(request.zip());
        Supplier saved = repository.save(supplier);
        SupplierResponse response = mapper.toResponse(saved);
        return response;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
