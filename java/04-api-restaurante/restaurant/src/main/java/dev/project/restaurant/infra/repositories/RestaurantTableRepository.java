package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}
