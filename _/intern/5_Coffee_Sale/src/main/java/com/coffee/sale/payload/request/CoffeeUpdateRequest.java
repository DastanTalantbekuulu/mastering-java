package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CoffeeUpdateRequest(
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
        Integer total
) {
}
