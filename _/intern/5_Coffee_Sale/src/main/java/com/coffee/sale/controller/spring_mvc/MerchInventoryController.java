package com.coffee.sale.controller.spring_mvc;

import com.coffee.sale.payload.response.MerchInventoryResponse;
import com.coffee.sale.service.MerchInventoryService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/inventory/merch")
public class MerchInventoryController {
    private final MerchInventoryService service;

    public MerchInventoryController(MerchInventoryService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping({"", "/page"})
    public String getAll(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size
    ) {
        Page<MerchInventoryResponse> merchInventoryResponses = service.findAll(page, size);
        model.addAttribute("page", merchInventoryResponses);
        return "merch_inventory";
    }
}
