package dev.project.restaurant.application.dtos;

import dev.project.restaurant.domain.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean available,
        Integer preparationTimeMinutes,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt
) {

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailable(),
                product.getPreparationTimeMinutes(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt()
        );
    }
}
