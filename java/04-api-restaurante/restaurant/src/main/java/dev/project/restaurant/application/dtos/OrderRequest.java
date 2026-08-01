package dev.project.restaurant.application.dtos;

import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull(message = "Table ID is required")
        Long tableId,

        String notes
) { }
