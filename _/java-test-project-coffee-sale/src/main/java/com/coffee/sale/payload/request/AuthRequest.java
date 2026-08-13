package com.coffee.sale.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank
        @Size(max = 255, min = 3)
        String username,
        @NotBlank
        @Size(min = 5)
        String password
) {
}
