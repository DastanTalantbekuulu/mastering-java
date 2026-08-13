package com.coffee.sale.controller.api;

import com.coffee.sale.payload.request.SupplierRequest;
import com.coffee.sale.payload.response.SupplierResponse;
import com.coffee.sale.service.SupplierService;
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
@RequestMapping("/api/coffee/supplier")
public class SupplierControllerAPI {
    private final SupplierService service;

    public SupplierControllerAPI(SupplierService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SupplierResponse>> getPage(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(10) Integer size
    ) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/{idSupplier}")
    public ResponseEntity<SupplierResponse> getById(@PathVariable("idSupplier") @Min(1) Integer idSupplier) {
        return ResponseEntity.ok(service.findById(idSupplier));
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> save(@Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping("/{idSupplier}")
    public ResponseEntity<SupplierResponse> update(
            @PathVariable("idSupplier") @Min(1) Integer idSupplier,
            @Valid @RequestBody SupplierRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(idSupplier, request));
    }

    @DeleteMapping("/{idSupplier}")
    public ResponseEntity<String> delete(@PathVariable("idSupplier") @Min(1) Integer idSupplier) {
        service.delete(idSupplier);
        return ResponseEntity.ok("Supplier deleted");
    }
}
