package com.coffee.sale.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SupplierResponse(
        @JsonProperty(value = "identifier")
        Integer id,
        @JsonProperty(value = "name_supplier")
        String name,
        @JsonProperty(value = "street_supplier")
        String street,
        @JsonProperty(value = "city_supplier")
        String city,
        @JsonProperty(value = "state_supplier")
        String state,
        @JsonProperty(value = "zip_supplier")
        String zip
) {
}
