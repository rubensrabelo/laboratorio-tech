package dev.project.restaurant.application.dtos;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        String paymentMethod
) {}
