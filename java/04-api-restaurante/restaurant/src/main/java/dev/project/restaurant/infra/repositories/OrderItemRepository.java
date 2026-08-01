package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByStatusOrderByIdAsc(OrderItemStatus status);

    List<OrderItem> findByOrderIdAndStatusNot(Long orderId, OrderItemStatus status);

    @Query("""
        SELECT i
        FROM OrderItem i
        JOIN FETCH i.product
        JOIN FETCH i.order o
        JOIN FETCH o.table
        WHERE i.status = :status
        ORDER BY i.id
    """)
    List<OrderItem> findItemsWithProductAndOrder(@Param("status") OrderItemStatus status);
}
