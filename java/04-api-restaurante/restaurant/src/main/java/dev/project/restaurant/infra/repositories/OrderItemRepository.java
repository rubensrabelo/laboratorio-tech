package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
