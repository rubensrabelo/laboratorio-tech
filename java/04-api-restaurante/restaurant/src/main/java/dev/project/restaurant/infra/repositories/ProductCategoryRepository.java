package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
