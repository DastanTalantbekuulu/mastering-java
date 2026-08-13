package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CoffeeAddRequest(
        @JsonProperty(value = "coffee")
        @NotBlank(message = "Coffee name is required")
        @Size(max = 32, message = "The coffee name cannot be longer than 32 characters.")
        String name,

        @JsonProperty(value = "rate")
        @NotNull(message = "Price must be specified")
        @Positive(message = "Price must be positive")
        @Digits(integer = 8, fraction = 2, message = "Price must have max 8 digits and 2 decimal places")
        Float price,

        @JsonProperty(value = "week")
        @Min(value = 0, message = "Sales cannot be negative")
        Integer sales,

        @JsonProperty(value = "all")
        @Min(value = 0, message = "Total cannot be negative")
        Integer total,

        @JsonProperty(value = "supplier_identifier")
        @NotNull(message = "Supplier must be specified")
        @Min(value = 1, message = "Supplier identifier cannot be empty")
        Integer supplier
) {
}
