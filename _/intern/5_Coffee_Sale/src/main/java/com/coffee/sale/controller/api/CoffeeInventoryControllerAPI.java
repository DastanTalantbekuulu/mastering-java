package com.coffee.sale.controller.api;

import com.coffee.sale.entity.coffee.CoffeeInventoryId;
import com.coffee.sale.payload.request.CoffeeInventoryRequest;
import com.coffee.sale.payload.response.CoffeeInventoryResponse;
import com.coffee.sale.service.CoffeeInventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
@RequestMapping("/api/coffee/inventory")
public class CoffeeInventoryControllerAPI {
    private final CoffeeInventoryService service;

    public CoffeeInventoryControllerAPI(CoffeeInventoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CoffeeInventoryResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/list")
    public ResponseEntity<List<Integer>> getList() {
        return ResponseEntity.ok(service.getList());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CoffeeInventoryResponse>> getPage(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size
    ) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/{idInventory}/{supplierId}/{coffee}")
    public ResponseEntity<CoffeeInventoryResponse> getById(
            @PathVariable("idInventory") @Min(1) Integer idInventory,
            @PathVariable("supplierId") @Min(1) Integer supplierId,
            @PathVariable("coffee") @Size(max = 32) String coffee
    ) {
        CoffeeInventoryId coffeeInventoryId = new CoffeeInventoryId(coffee, supplierId, idInventory);
        return ResponseEntity.ok(service.findById(coffeeInventoryId));
    }

    @PostMapping
    public ResponseEntity<CoffeeInventoryResponse> save(@Valid @RequestBody CoffeeInventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping
    public ResponseEntity<CoffeeInventoryResponse> update(
            @Valid @RequestBody CoffeeInventoryRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(request));
    }

    @DeleteMapping("/{idInventory}/{supplierId}/{coffee}")
    public ResponseEntity<String> delete(
            @PathVariable("idInventory") @Min(1) Integer idInventory,
            @PathVariable("supplierId") @Min(1) Integer supplierId,
            @PathVariable("coffee") @Size(max = 32) String coffee
    ) {
        CoffeeInventoryId coffeeInventoryId = new CoffeeInventoryId(coffee, supplierId, idInventory);
        service.delete(coffeeInventoryId);
        return ResponseEntity.ok("Coffee Inventory deleted");
    }
}
