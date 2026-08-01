package dev.project.restaurant.application.dtos;

import dev.project.restaurant.domain.Order;
import dev.project.restaurant.domain.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long tableId,
        Integer tableNumber,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        OrderStatus status,
        String notes
) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTableId(),
                order.getTableNumber(),
                order.getOpenedAt(),
                order.getClosedAt(),
                order.getStatus(),
                order.getNotes()
        );
    }
}

