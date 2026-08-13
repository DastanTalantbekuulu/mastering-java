package com.coffee.sale.entity.coffee;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record CoffeeId(
        @NotBlank(message = "Coffee name is required")
        @JsonProperty(value = "coffee_name")
        @Column(name = "cof_name", unique = true, length = 32)
        String name,

        @NotNull(message = "Supplier must be specified")
        @Min(value = 1, message = "Supplier identifier cannot be empty")
        @JsonProperty(value = "supplier_identifier")
        @Column(name = "sup_id")
        Integer supplier
) {
}
