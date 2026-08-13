package com.coffee.sale.controller.api;

import com.coffee.sale.entity.coffee.CoffeeId;
import com.coffee.sale.payload.request.CoffeeAddRequest;
import com.coffee.sale.payload.request.CoffeeRequest;
import com.coffee.sale.payload.request.CoffeeUpdateRequest;
import com.coffee.sale.payload.response.CoffeeResponse;
import com.coffee.sale.service.CoffeeService;
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
@RequestMapping("/api/coffee")
public class CoffeeControllerAPI {
    private final CoffeeService service;

    public CoffeeControllerAPI(CoffeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CoffeeResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CoffeeResponse>> getPage(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(10) Integer size
    ) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/{supplierId}/{coffee}")
    public ResponseEntity<CoffeeResponse> getById(
            @PathVariable("supplierId") @Min(1) Integer supplierId,
            @PathVariable("coffee") @Size(max = 32) String coffee
    ) {
        CoffeeId coffeeId = new CoffeeId(coffee, supplierId);
        return ResponseEntity.ok(service.findById(coffeeId));
    }

    @PostMapping
    public ResponseEntity<CoffeeResponse> save(@Valid @RequestBody CoffeeRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(request));
    }

    @PostMapping("/add")
    public ResponseEntity<CoffeeResponse> add(@Valid @RequestBody CoffeeAddRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.add(request));
    }

    @PostMapping("/{supplierId}/{coffee}/sale/{sales}")
    public ResponseEntity<CoffeeResponse> sale(
            @PathVariable("supplierId") @Valid @Min(1) Integer supplierId,
            @PathVariable("coffee") @Valid @Size(max = 32) String coffee,
            @PathVariable("sales") @Valid @Min(1) Integer sales
    ) {
        CoffeeId coffeeId = new CoffeeId(coffee, supplierId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.update(coffeeId, sales));
    }

    @PutMapping("/{supplierId}/{coffee}")
    public ResponseEntity<CoffeeResponse> update(
            @PathVariable("supplierId") @Min(1) Integer supplierId,
            @PathVariable("coffee") @Size(max = 32) String coffee,
            @Valid @RequestBody CoffeeUpdateRequest request
    ) {
        CoffeeId coffeeId = new CoffeeId(coffee, supplierId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.update(coffeeId, request));
    }

    @DeleteMapping("/{supplierId}/{coffee}")
    public ResponseEntity<String> delete(
            @PathVariable("supplierId") @Min(1) Integer supplierId,
            @PathVariable("coffee") @Size(max = 32) String coffee
    ) {
        CoffeeId coffeeId = new CoffeeId(coffee, supplierId);
        service.delete(coffeeId);
        return ResponseEntity.ok("Coffee deleted");
    }
}
