package com.coffee.sale.controller.spring_mvc;

import com.coffee.sale.payload.response.CoffeeHouseResponse;
import com.coffee.sale.service.CoffeeHouseService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/coffeehouse")
@PreAuthorize("hasAuthority('ADMIN')")
public class CoffeeHouseController {
    private final CoffeeHouseService service;

    public CoffeeHouseController(CoffeeHouseService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping({"", "/page"})
    public String getPage(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(10) Integer size
    ) {
        Page<CoffeeHouseResponse> pageResponse = service.findAll(page, size);
        model.addAttribute("page", pageResponse);
        return "coffeehouse";
    }
}
