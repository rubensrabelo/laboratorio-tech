package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
