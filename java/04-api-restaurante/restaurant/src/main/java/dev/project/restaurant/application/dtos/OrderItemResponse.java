package dev.project.restaurant.application.dtos;

import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long orderId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        String notes,
        OrderItemStatus status
) {

    public static OrderItemResponse fromEntity(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getOrderId(),     
                item.getProductId(),   
                item.getProductName(), 
                item.getQuantity(),
                item.getUnitPrice(),
                item.getNotes(),
                item.getStatus()
        );
    }
}
