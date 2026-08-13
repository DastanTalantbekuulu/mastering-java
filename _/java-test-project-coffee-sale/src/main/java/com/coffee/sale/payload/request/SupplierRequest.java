package com.coffee.sale.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierRequest(
        @JsonProperty(value = "name_supplier")
        @NotBlank(message = "Supplier name is required")
        @Size(max = 255, message = "Supplier name cannot exceed 255 characters")
        String name,

        @JsonProperty(value = "street_supplier")
        @NotBlank(message = "Street is required")
        @Size(max = 255, message = "Street name cannot exceed 255 characters")
        String street,

        @JsonProperty(value = "city_supplier")
        @NotBlank(message = "City is required")
        @Size(max = 255, message = "City name cannot exceed 255 characters")
        String city,

        @JsonProperty(value = "state_supplier")
        @NotBlank(message = "Country is required")
        @Size(max = 255, message = "Country name cannot exceed 255 characters")
        String state,

        @JsonProperty(value = "zip_supplier")
        @NotBlank(message = "Postal code is required")
        @Size(max = 255, message = "Postal code cannot exceed 255 characters")
        String zip
) {
}
