package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CoffeeHouseRequest(
        @JsonProperty(value = "identifier")
        @NotNull(message = "Store identifier is required")
        @Min(value = 1, message = "Store identifier cannot be empty")
        Integer storeId,

        @JsonProperty(value = "city")
        @NotBlank(message = "City is required")
        @Size(max = 255, message = "City name cannot exceed 255 characters")
        String city,

        @JsonProperty(value = "coffee_sold")
        @NotNull
        @Min(value = 0, message = "Coffee quantity pounds cannot be negative")
        Integer coffee,

        @JsonProperty(value = "merch_sold")
        @NotNull
        @Min(value = 0, message = "Merch quantity cannot be negative")
        Integer merch
) {
}
