package dev.project.mastersys.application.dtos;

import java.math.BigDecimal;

public record MonthlyBillingResponseDTO(
    String month,
    BigDecimal total
) {}
