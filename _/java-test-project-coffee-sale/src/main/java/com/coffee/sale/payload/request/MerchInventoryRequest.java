package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchInventoryRequest(
        @JsonProperty(value = "identifier")
        @NotNull(message = "Merch identifier is required")
        @Min(value = 1, message = "Merch identifier cannot be empty")
        Integer id,

        @JsonProperty(value = "name")
        @NotBlank(message = "Merch name is required")
        @Size(max = 255, message = "Merch name cannot exceed 255 characters")
        String name,

        @JsonProperty(value = "supplier_identifier")
        @NotNull(message = "Supplier identifier is required")
        @Min(value = 1, message = "Supplier identifier cannot be empty")
        Integer supplierId,

        @JsonProperty(value = "merch_quantity")
        @NotNull(message = "Merch quantity is required")
        @Min(value = 0, message = "Merch quantity cannot be negative")
        Integer quantity
) {
}
