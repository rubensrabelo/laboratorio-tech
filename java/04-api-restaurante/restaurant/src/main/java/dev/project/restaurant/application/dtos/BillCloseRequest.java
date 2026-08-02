package dev.project.restaurant.application.dtos;

import java.math.BigDecimal;

public record BillCloseRequest(
        BigDecimal serviceFee,
        BigDecimal discount
) {
}
