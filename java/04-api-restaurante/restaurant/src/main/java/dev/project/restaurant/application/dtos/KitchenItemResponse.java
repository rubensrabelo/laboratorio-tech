package dev.project.restaurant.application.dtos;

import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import java.math.BigDecimal;

public record KitchenItemResponse(
        Long itemId,
        Long orderId,
        Integer tableNumber,
        String productName,
        Integer quantity,
        String observation,
        BigDecimal unitPrice,
        OrderItemStatus status
) {

    public static KitchenItemResponse fromEntity(OrderItem item) {
        return new KitchenItemResponse(
                item.getId(),
                item.getOrderId(),
                item.getOrderTableNumber(),
                item.getProductName(),
                item.getQuantity(),
                item.getNotes(),
                item.getUnitPrice(),
                item.getStatus()
        );
    }
}
