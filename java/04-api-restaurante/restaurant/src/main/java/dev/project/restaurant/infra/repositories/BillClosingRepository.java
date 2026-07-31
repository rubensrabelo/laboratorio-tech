package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.BillClosing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillClosingRepository extends JpaRepository<BillClosing, Long> {
}
