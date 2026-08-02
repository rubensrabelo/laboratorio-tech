package dev.project.restaurant.application.services;

import dev.project.restaurant.application.dtos.KitchenItemResponse;
import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import dev.project.restaurant.exceptions.domain.BusinessException;
import dev.project.restaurant.exceptions.domain.ResourceNotFoundException;
import dev.project.restaurant.infra.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KitchenService {

    private final OrderItemRepository orderItemRepository;

    public KitchenService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(readOnly = true)
    public List<KitchenItemResponse> listPendingItems() {
        return orderItemRepository.findByStatusOrderByIdAsc(OrderItemStatus.PENDING)
                .stream()
                .map(KitchenItemResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KitchenItemResponse> listPreparingItems() {
        return orderItemRepository.findByStatusOrderByIdAsc(OrderItemStatus.PREPARING)
                .stream()
                .map(KitchenItemResponse::fromEntity)
                .toList();
    }

    @Transactional
    public KitchenItemResponse startPreparation(Long itemId) {
        OrderItem item = findOrderItemEntityById(itemId);

        if (item.getStatus() != OrderItemStatus.PENDING) {
            throw new BusinessException("Only pending items can start preparation.");
        }

        item.setStatus(OrderItemStatus.PREPARING);
        item.setPreparationStartedAt(LocalDateTime.now());

        return KitchenItemResponse.fromEntity(orderItemRepository.save(item));
    }

    @Transactional
    public KitchenItemResponse markAsReady(Long itemId) {
        OrderItem item = findOrderItemEntityById(itemId);

        if (item.getStatus() != OrderItemStatus.PREPARING) {
            throw new BusinessException("Only items in preparation can be marked as ready.");
        }

        item.setStatus(OrderItemStatus.READY);
        item.setReadyAt(LocalDateTime.now());

        return KitchenItemResponse.fromEntity(orderItemRepository.save(item));
    }

    @Transactional
    public KitchenItemResponse deliverItem(Long itemId) {
        OrderItem item = findOrderItemEntityById(itemId);

        if (item.getStatus() != OrderItemStatus.READY) {
            throw new BusinessException("Only ready items can be delivered.");
        }

        item.setStatus(OrderItemStatus.DELIVERED);
        item.setDeliveredAt(LocalDateTime.now());

        return KitchenItemResponse.fromEntity(orderItemRepository.save(item));
    }

    private OrderItem findOrderItemEntityById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with ID: " + id));
    }
}
