package com.coffee.sale.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoffeeHouseResponse(
        @JsonProperty(value = "identifier")
        Integer storeId,

        @JsonProperty(value = "city")
        String city,

        @JsonProperty(value = "coffee_sold")
        Integer coffee,

        @JsonProperty(value = "other_sold")
        Integer merch,

        @JsonProperty(value = "all")
        Integer total
) {
}
