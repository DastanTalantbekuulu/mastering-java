package com.coffee.sale.service;

import com.coffee.sale.payload.request.SupplierRequest;
import com.coffee.sale.payload.response.SupplierResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

public interface SupplierService {
    List<SupplierResponse> findAll();

    Page<SupplierResponse> findAll(int page, int size);

    SupplierResponse findById(Integer id);

    @PreAuthorize("hasAuthority('ADMIN')")
    SupplierResponse save(SupplierRequest request);

    @PreAuthorize("hasAuthority('ADMIN')")
    SupplierResponse update(Integer id, SupplierRequest request);

    @PreAuthorize("hasAuthority('OWNER')")
    void delete(Integer id);
}
