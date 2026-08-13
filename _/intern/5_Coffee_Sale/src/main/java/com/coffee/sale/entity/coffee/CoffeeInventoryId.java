package com.coffee.sale.entity.coffee;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoffeeInventoryId(
        @JsonProperty(value = "coffee_name")
        @NotBlank(message = "Coffee name is required")
        @Column(name = "cof_name", unique = true, length = 32)
        String coffeeName,

        @JsonProperty(value = "supplier_identifier")
        @NotNull(message = "Supplier must be specified")
        @Min(value = 1, message = "Supplier identifier cannot be empty")
        @Column(name = "sup_id")
        Integer supplierId,

        @JsonProperty(value = "warehouse_identifier")
        @NotNull(message = "Warehouse must be specified")
        @Min(value = 1, message = "Warehouse identifier cannot be empty")
        @Column(name = "warehouse_id")
        Integer warehouseId
) {
}