package com.coffee.sale.controller.api;

import com.coffee.sale.payload.request.CoffeeHouseRequest;
import com.coffee.sale.payload.response.CoffeeHouseResponse;
import com.coffee.sale.service.CoffeeHouseService;
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
@RequestMapping("/api/coffee/coffeehouse")
public class CoffeeHouseControllerAPI {
    private final CoffeeHouseService service;

    public CoffeeHouseControllerAPI(CoffeeHouseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CoffeeHouseResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CoffeeHouseResponse>> getPage(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(10) Integer size
    ) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/{idCoffeeHouse}")
    public ResponseEntity<CoffeeHouseResponse> getById(@PathVariable("idCoffeeHouse") Integer idCoffeeHouse) {
        return ResponseEntity.ok(service.findById(idCoffeeHouse));
    }

    @PostMapping
    public ResponseEntity<CoffeeHouseResponse> save(@Valid @RequestBody CoffeeHouseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping("/{idCoffeeHouse}")
    public ResponseEntity<CoffeeHouseResponse> update(
            @PathVariable("idCoffeeHouse") @Min(1) Integer idCoffeeHouse,
            @Valid @RequestBody CoffeeHouseRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(idCoffeeHouse, request));
    }

    @DeleteMapping("/{idCoffeeHouse}")
    public ResponseEntity<String> delete(@PathVariable("idCoffeeHouse") @Min(1) Integer idCoffeeHouse) {
        service.delete(idCoffeeHouse);
        return ResponseEntity.ok("CoffeeHouse deleted");
    }
}
