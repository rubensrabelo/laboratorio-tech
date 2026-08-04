package dev.project.restaurant.infra.repositories;

import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o FROM OrderItem o WHERE o.order.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    List<OrderItem> findByStatusOrderByIdAsc(OrderItemStatus status);

    @Query("SELECT o FROM OrderItem o WHERE o.order.id = :orderId AND o.status <> :status")
    List<OrderItem> findByOrderIdAndStatusNot(@Param("orderId") Long orderId, @Param("status") OrderItemStatus status);

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
