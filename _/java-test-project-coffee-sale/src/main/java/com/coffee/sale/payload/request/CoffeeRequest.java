package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CoffeeRequest(
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
        @NotNull(message = "message  is required")
        @Min(value = 0, message = "week must be specified")
        Integer sales,

        @JsonProperty(value = "all")
        @NotNull(message = "all  is required")
        @Min(value = 0, message = "all must be specified")
        Integer total,

        @JsonProperty(value = "supplier")
        @NotNull(message = "Supplier must be specified")
        SupplierRequest supplier
) {
}
