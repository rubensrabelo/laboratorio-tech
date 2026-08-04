package dev.project.restaurant.domain.enums;

public enum PaymentMethod {
    CASH,
    CREDIT_CARD,
    DEBIT_CARD,
    PIX;

    public static PaymentMethod fromString(String paymentMethodStr) {
        if (paymentMethodStr == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        try {
            return PaymentMethod.valueOf(paymentMethodStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid payment method: " + paymentMethodStr);
        }
    }
}
