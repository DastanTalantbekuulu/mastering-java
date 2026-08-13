package com.coffee.sale.controller.spring_mvc;

import com.coffee.sale.payload.response.CoffeeResponse;
import com.coffee.sale.service.CoffeeService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {
    private final CoffeeService service;

    public CoffeeController(CoffeeService service) {
        this.service = service;
    }

    @GetMapping({"", "/page"})
    public String getAll(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size
    ) {
        Page<CoffeeResponse> coffeeResponses = service.findAll(page, size);
        model.addAttribute("page", coffeeResponses);
        return "coffee";
    }
}
