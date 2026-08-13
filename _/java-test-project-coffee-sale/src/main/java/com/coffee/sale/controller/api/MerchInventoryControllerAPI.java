package com.coffee.sale.controller.api;

import com.coffee.sale.payload.request.MerchInventoryRequest;
import com.coffee.sale.payload.request.MerchInventoryUpdateRequest;
import com.coffee.sale.payload.response.MerchInventoryResponse;
import com.coffee.sale.service.MerchInventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coffee/merch/inventory")
public class MerchInventoryControllerAPI {
    private final MerchInventoryService service;

    public MerchInventoryControllerAPI(MerchInventoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MerchInventoryResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<MerchInventoryResponse>> getPage(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size
    ) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/{idMerchInventory}")
    public ResponseEntity<MerchInventoryResponse> getById(
            @PathVariable("idMerchInventory") @Min(1) Integer idMerchInventory
    ) {
        return ResponseEntity.ok(service.findById(idMerchInventory));
    }

    @PostMapping
    public ResponseEntity<MerchInventoryResponse> save(@Valid @RequestBody MerchInventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping("/{idMerchInventory}")
    public ResponseEntity<MerchInventoryResponse> update(
            @PathVariable("idMerchInventory") @Min(1) Integer idMerchInventory,
            @Valid @RequestBody MerchInventoryUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(idMerchInventory, request));
    }

    @DeleteMapping("/{idMerchInventory}")
    public ResponseEntity<String> delete(@PathVariable("idMerchInventory") @Min(1) Integer idMerchInventory) {
        service.delete(idMerchInventory);
        return ResponseEntity.ok("Merch Inventory deleted");
    }
}
