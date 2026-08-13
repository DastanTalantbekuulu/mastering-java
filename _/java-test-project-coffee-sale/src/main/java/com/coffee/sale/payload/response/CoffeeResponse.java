package com.coffee.sale.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoffeeResponse(
        @JsonProperty(value = "coffee")
        String name,
        @JsonProperty(value = "rate")
        Float price,
        @JsonProperty(value = "week")
        Integer sales,
        @JsonProperty(value = "all")
        Integer total,
        @JsonProperty(value = "supplier")
        SupplierResponse supplier
) {
}
