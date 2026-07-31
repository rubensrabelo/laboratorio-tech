package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
