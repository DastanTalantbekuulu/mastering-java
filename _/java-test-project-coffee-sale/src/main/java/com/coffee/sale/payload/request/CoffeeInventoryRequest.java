package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CoffeeInventoryRequest(
        @JsonProperty(value = "coffee_house_identifier")
        @NotNull(message = "Identifier is required")
        @Min(value = 1, message = "Identifier cannot be empty")
        Integer warehouseId,

        @JsonProperty(value = "coffee")
        @NotBlank(message = "Coffee name is required")
        @Size(max = 32, message = "The coffee name cannot be longer than 32 characters.")
        String coffee,

        @JsonProperty(value = "supplier_identifier")
        @NotNull(message = "Supplier identifier is required")
        @Min(value = 1, message = "Supplier identifier cannot be empty")
        Integer supplierId,

        @JsonProperty(value = "coffee_quantity")
        @NotNull
        @Min(value = 0, message = "Coffee quantity cannot be negative")
        Integer quantity
) {
}
