package dev.project.restaurant.application.services;

import dev.project.restaurant.application.dtos.OrderItemRequest;
import dev.project.restaurant.application.dtos.OrderItemResponse;
import dev.project.restaurant.application.dtos.OrderRequest;
import dev.project.restaurant.application.dtos.OrderResponse;
import dev.project.restaurant.domain.Order;
import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.Product;
import dev.project.restaurant.domain.RestaurantTable;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import dev.project.restaurant.domain.enums.OrderStatus;
import dev.project.restaurant.domain.enums.TableStatus;
import dev.project.restaurant.exceptions.domain.BusinessException;
import dev.project.restaurant.exceptions.domain.ResourceNotFoundException;
import dev.project.restaurant.infra.repositories.OrderItemRepository;
import dev.project.restaurant.infra.repositories.OrderRepository;
import dev.project.restaurant.infra.repositories.ProductRepository;
import dev.project.restaurant.infra.repositories.RestaurantTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantTableRepository tableRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            RestaurantTableRepository tableRepository,
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderResponse openOrder(OrderRequest request) {
        RestaurantTable table = tableRepository.findById(request.tableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with ID: " + request.tableId()));

        if (table.getStatus() != TableStatus.AVAILABLE) {
            throw new BusinessException("Table is not available to open an order.");
        }

        Order order = new Order();
        order.setTable(table);
        order.setStatus(OrderStatus.OPEN);
        order.setNotes(request.notes());

        table.setStatus(TableStatus.OCCUPIED);

        Order savedOrder = orderRepository.save(order);
        tableRepository.save(table);

        return OrderResponse.fromEntity(savedOrder);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> listAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = findOrderEntityById(id);
        return OrderResponse.fromEntity(order);
    }

    @Transactional
    public OrderItemResponse addItem(Long orderId, OrderItemRequest request) {
        Order order = findOrderEntityById(orderId);

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new BusinessException("Items can only be added to open orders.");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.productId()));

        if (!product.getAvailable()) {
            throw new BusinessException("Product is unavailable on the menu.");
        }

        if (request.quantity() == null || request.quantity() <= 0) {
            throw new BusinessException("Quantity must be greater than zero.");
        }

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.quantity());
        item.setUnitPrice(product.getPrice());
        item.setNotes(request.notes());
        item.setStatus(OrderItemStatus.PENDING);

        OrderItem savedItem = orderItemRepository.save(item);

        return OrderItemResponse.fromEntity(savedItem);
    }

    @Transactional(readOnly = true)
    public List<OrderItemResponse> listItems(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }

        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(OrderItemResponse::fromEntity)
                .toList();
    }

    private Order findOrderEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }
}
