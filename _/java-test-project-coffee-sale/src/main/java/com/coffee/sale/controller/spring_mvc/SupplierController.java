package com.coffee.sale.controller.spring_mvc;

import com.coffee.sale.payload.response.SupplierResponse;
import com.coffee.sale.service.SupplierService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping({"", "/page"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAll(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size
    ) {
        Page<SupplierResponse> supplierResponses = service.findAll(page, size);
        model.addAttribute("page", supplierResponses);
        return "supplier";
    }
}
