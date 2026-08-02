package dev.project.restaurant.application.dtos;

import dev.project.restaurant.domain.BillClosing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillCloseResponse(
        Long id,
        Long orderId,
        Integer tableNumber,
        BigDecimal subtotal,
        BigDecimal serviceFee,
        BigDecimal discount,
        BigDecimal total,
        LocalDateTime closedAt
) {

    public static BillCloseResponse fromEntity(BillClosing billClose) {
        return new BillCloseResponse(
                billClose.getId(),
                billClose.getOrderId(),
                billClose.getOrderTableNumber(),
                billClose.getSubtotal(),
                billClose.getServiceFee(),
                billClose.getDiscount(),
                billClose.getTotal(),
                billClose.getClosedAt()
        );
    }
}
