package com.coffee.sale.service;

import com.coffee.sale.payload.request.MerchInventoryRequest;
import com.coffee.sale.payload.request.MerchInventoryUpdateRequest;
import com.coffee.sale.payload.response.MerchInventoryResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('ADMIN')")
public interface MerchInventoryService {

    List<MerchInventoryResponse> findAll();

    Page<MerchInventoryResponse> findAll(int page, int size);

    MerchInventoryResponse findById(Integer id);

    MerchInventoryResponse save(MerchInventoryRequest request);

    MerchInventoryResponse update(Integer id, MerchInventoryUpdateRequest request);

    void delete(Integer id);
}
