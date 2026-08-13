package com.coffee.sale.repository;

import com.coffee.sale.entity.coffee.CoffeeHouse;
import com.coffee.sale.mapper.CoffeeHouseMapper;
import com.coffee.sale.payload.request.CoffeeHouseRequest;
import com.coffee.sale.payload.response.CoffeeHouseResponse;
import com.coffee.sale.repository.jpa.coffee.CoffeeHouseJpa;
import com.coffee.sale.service.CoffeeHouseService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CoffeeHouseRepository implements CoffeeHouseService {
    private final CoffeeHouseJpa repository;
    private final CoffeeHouseMapper mapper;

    public CoffeeHouseRepository(CoffeeHouseJpa repository, CoffeeHouseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CoffeeHouseResponse> findAll() {
        List<CoffeeHouse> all = repository.findAll();
        List<CoffeeHouseResponse> response = mapper.toResponse(all);
        return response;
    }

    public Page<CoffeeHouseResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("storeId"));
        Page<CoffeeHouse> coffeeHouses = repository.findAll(pageRequest);
        Page<CoffeeHouseResponse> coffeeHouseResponses = coffeeHouses.map(mapper::toResponse);
        return coffeeHouseResponses;
    }

    public CoffeeHouseResponse findById(Integer id) {
        CoffeeHouse coffeeHouse = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coffee House not found"));
        CoffeeHouseResponse response = mapper.toResponse(coffeeHouse);
        return response;
    }

    public CoffeeHouseResponse save(CoffeeHouseRequest request) {
        CoffeeHouse coffeeHouse = mapper.toEntity(request);
        CoffeeHouse saved = repository.save(coffeeHouse);
        CoffeeHouseResponse response = mapper.toResponse(saved);
        return response;
    }

    public CoffeeHouseResponse update(Integer id, CoffeeHouseRequest request) {
        CoffeeHouse coffeeHouse = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coffee House not found"));
        coffeeHouse.setCity(request.city());
        coffeeHouse.setCoffee(request.coffee());
        coffeeHouse.setMerch(request.merch());
        coffeeHouse.setTotal(request.merch() + request.coffee());
        CoffeeHouse saved = repository.save(coffeeHouse);
        CoffeeHouseResponse response = mapper.toResponse(saved);
        return response;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public boolean exists(Integer id) {
        return repository.existsById(id);
    }
}
