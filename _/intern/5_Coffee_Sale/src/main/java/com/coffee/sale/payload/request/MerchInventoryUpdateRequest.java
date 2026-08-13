package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchInventoryUpdateRequest(
        @JsonProperty(value = "name")
        @NotBlank(message = "Merch name is required")
        @Size(max = 255, message = "Merch name cannot exceed 255 characters")
        String name,

        @JsonProperty(value = "merch_quantity")
        @NotNull(message = "Merch quantity is required")
        @Min(value = 0, message = "Merch quantity cannot be negative")
        Integer quantity
) {
}
