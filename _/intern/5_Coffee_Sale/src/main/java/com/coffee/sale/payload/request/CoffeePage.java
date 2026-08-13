package com.coffee.sale.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CoffeePage(
        @NotNull
        @Min(0)
        Integer page,
        @NotNull
        @Min(1)
        Integer size
) {
}
