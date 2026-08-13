package com.coffee.sale.service;

import com.coffee.sale.entity.coffee.CoffeeId;
import com.coffee.sale.payload.request.CoffeeAddRequest;
import com.coffee.sale.payload.request.CoffeeRequest;
import com.coffee.sale.payload.request.CoffeeUpdateRequest;
import com.coffee.sale.payload.response.CoffeeResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CoffeeService {

    List<CoffeeResponse> findAll();

    Page<CoffeeResponse> findAll(int page, int size);

    CoffeeResponse findById(CoffeeId coffeeId);

    @PreAuthorize("hasAuthority('ADMIN')")
    CoffeeResponse save(CoffeeRequest request);

    @PreAuthorize("hasAuthority('ADMIN')")
    CoffeeResponse add(CoffeeAddRequest request);

    CoffeeResponse update(CoffeeId coffeeId, Integer sales);

    @PreAuthorize("hasAuthority('ADMIN')")
    CoffeeResponse update(CoffeeId coffeeId, CoffeeUpdateRequest request);

    @PreAuthorize("hasAuthority('OWNER')")
    void delete(CoffeeId coffeeId);

    boolean exists(CoffeeId coffeeId);

}
