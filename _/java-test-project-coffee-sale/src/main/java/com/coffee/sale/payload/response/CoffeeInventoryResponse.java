package com.coffee.sale.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record CoffeeInventoryResponse(
        @JsonProperty(value = "identifier")
        Integer warehouseId,
        @JsonProperty(value = "coffee")
        String coffee,
        @JsonProperty(value = "supplier_identifier")
        Integer supplierId,
        @JsonProperty(value = "coffee_quan")
        Integer quantity,
        @JsonProperty(value = "last_updated")
        LocalDate updatedAt
) {
}
