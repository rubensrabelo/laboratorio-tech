package dev.project.restaurant.application.services;

import dev.project.restaurant.application.dtos.BillCloseRequest;
import dev.project.restaurant.application.dtos.BillCloseResponse;
import dev.project.restaurant.domain.BillClosing;
import dev.project.restaurant.domain.Order;
import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import dev.project.restaurant.domain.enums.OrderStatus;
import dev.project.restaurant.exceptions.domain.BusinessException;
import dev.project.restaurant.exceptions.domain.ResourceNotFoundException;
import dev.project.restaurant.infra.repositories.BillClosingRepository;
import dev.project.restaurant.infra.repositories.OrderItemRepository;
import dev.project.restaurant.infra.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillCloseService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BillClosingRepository billClosingRepository;

    public BillCloseService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            BillClosingRepository billClosingRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.billClosingRepository = billClosingRepository;
    }

    @Transactional
    public BillCloseResponse closeBill(Long orderId, BillCloseRequest request) {
        Order order = findOrderEntityById(orderId);

        if (order.getStatus() == OrderStatus.CLOSED) {
            throw new BusinessException("Order is already closed.");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException("Canceled order cannot be closed.");
        }

        if (billClosingRepository.existsByOrderId(orderId)) {
            throw new BusinessException("A bill closure already exists for this order.");
        }

        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);

        if (items.isEmpty()) {
            throw new BusinessException("Cannot close a bill with no items.");
        }

        List<OrderItem> undeliveredItems = 
                orderItemRepository.findByOrderIdAndStatusNot(orderId, OrderItemStatus.DELIVERED);

        if (!undeliveredItems.isEmpty()) {
            throw new BusinessException("All items must be delivered before closing the bill.");
        }

        BigDecimal subtotal = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal serviceFee = request.serviceFee() != null ? request.serviceFee() : BigDecimal.ZERO;
        BigDecimal discount = request.discount() != null ? request.discount() : BigDecimal.ZERO;

        if (serviceFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Service fee cannot be negative.");
        }

        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Discount cannot be negative.");
        }

        BigDecimal total = subtotal.add(serviceFee).subtract(discount);

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Bill total cannot be negative.");
        }

        BillClosing billClose = new BillClosing();
        billClose.setOrder(order);
        billClose.setSubtotal(subtotal);
        billClose.setServiceFee(serviceFee);
        billClose.setDiscount(discount);
        billClose.setTotal(total);
        billClose.setClosedAt(LocalDateTime.now());

        order.setStatus(OrderStatus.CLOSED);
        order.setClosedAt(LocalDateTime.now());

        BillClosing savedBillClose = billClosingRepository.save(billClose);
        orderRepository.save(order);

        return BillCloseResponse.fromEntity(savedBillClose);
    }

    @Transactional(readOnly = true)
    public BillCloseResponse findByOrderId(Long orderId) {
        BillClosing billClose = billClosingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill closure not found for order ID: " + orderId));

        return BillCloseResponse.fromEntity(billClose);
    }

    private Order findOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
    }
}
