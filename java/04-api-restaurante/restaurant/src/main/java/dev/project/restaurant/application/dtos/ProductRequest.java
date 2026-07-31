package dev.project.restaurant.application.dtos;

import dev.project.restaurant.domain.Product;
import dev.project.restaurant.domain.ProductCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "Category ID is required")
        Long categoryId,

        @NotBlank(message = "Product name cannot be blank")
        @Size(max = 150, message = "Product name must not exceed 150 characters")
        String name,

        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0")
        BigDecimal price,

        Boolean available,

        Integer preparationTimeMinutes
) {

    public Product toEntity(ProductCategory category) {
        Product product = new Product();
        fill(product, category);
        return product;
    }

    public void fill(Product product, ProductCategory category) {
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setAvailable(available != null ? available : true);
        product.setPreparationTimeMinutes(preparationTimeMinutes);
    }
}
