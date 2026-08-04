package dev.project.restaurant.application.dtos;

public record PaymentResponse(
        String status,
        String transactionCode
) {}
