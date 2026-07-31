package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
