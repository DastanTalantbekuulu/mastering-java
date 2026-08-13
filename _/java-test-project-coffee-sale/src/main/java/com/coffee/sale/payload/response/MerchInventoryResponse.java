package com.coffee.sale.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record MerchInventoryResponse(
        @JsonProperty(value = "identifier")
        Integer id,
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "supplier_identifier")
        Integer supplierId,
        @JsonProperty(value = "merch_quantity")
        Integer quantity,
        @JsonProperty(value = "last_updated")
        LocalDate updatedAt
) {
}
