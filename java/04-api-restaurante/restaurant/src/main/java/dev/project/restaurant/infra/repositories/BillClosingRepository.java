package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.BillClosing;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillClosingRepository extends JpaRepository<BillClosing, Long> {

    @Query("SELECT COUNT(b) > 0 FROM BillClosing b WHERE b.order.id = :orderId")
    boolean existsByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT b FROM BillClosing b WHERE b.order.id = :orderId")
    Optional<BillClosing> findByOrderId(@Param("orderId") Long orderId);
}
