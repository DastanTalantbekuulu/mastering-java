package com.coffee.sale.controller.spring_mvc;

import com.coffee.sale.payload.response.CoffeeInventoryResponse;
import com.coffee.sale.service.CoffeeInventoryService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/inventory/coffee")
public class CoffeeInventoryController {
    private final CoffeeInventoryService service;

    public CoffeeInventoryController(CoffeeInventoryService service) {
        this.service = service;
    }

    @GetMapping({"", "/page"})
    public String getAll(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size
    ) {
        Page<CoffeeInventoryResponse> coffeeInventoryResponses = service.findAll(page, size);
        model.addAttribute("page", coffeeInventoryResponses);
        return "coffee_inventory";
    }
}
