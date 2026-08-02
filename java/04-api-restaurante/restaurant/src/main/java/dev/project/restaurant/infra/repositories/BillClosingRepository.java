package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.BillClosing;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillClosingRepository extends JpaRepository<BillClosing, Long> {

    boolean existsByOrderId(Long pedidoId);

    Optional<BillClosing> findByOrderId(Long pedidoId);
}
