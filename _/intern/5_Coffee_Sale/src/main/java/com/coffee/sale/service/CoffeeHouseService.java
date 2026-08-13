package com.coffee.sale.service;

import com.coffee.sale.payload.request.CoffeeHouseRequest;
import com.coffee.sale.payload.response.CoffeeHouseResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('ADMIN')")
public interface CoffeeHouseService {

    List<CoffeeHouseResponse> findAll();

    Page<CoffeeHouseResponse> findAll(int page, int size);

    CoffeeHouseResponse findById(Integer id);

    @PreAuthorize("hasAuthority('OWNER')")
    CoffeeHouseResponse save(CoffeeHouseRequest request);

    @PreAuthorize("hasAuthority('OWNER')")
    CoffeeHouseResponse update(Integer id, CoffeeHouseRequest request);

    @PreAuthorize("hasAuthority('OWNER')")
    void delete(Integer id);

    boolean exists(Integer id);
}
