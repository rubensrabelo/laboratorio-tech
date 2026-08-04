package dev.project.restaurant.worker;

import dev.project.restaurant.domain.OrderItem;
import dev.project.restaurant.domain.enums.OrderItemStatus;
import dev.project.restaurant.infra.repositories.OrderItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KitchenWorker {

    private final OrderItemRepository orderItemRepository;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public KitchenWorker(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void checkDelayedItems() {
        List<OrderItem> preparingItems = orderItemRepository.findItemsWithProductAndOrder(OrderItemStatus.PREPARING);
        
        for (OrderItem item : preparingItems) {
            executorService.submit(() -> checkItem(item));
        }
    }

    private void checkItem(OrderItem item) {
        if (item.getPreparationStartedAt() == null) {
            return;
        }


        Integer prepTime = item.getProductPreparationTimeMinutes();
        if (prepTime == null || prepTime <= 0) {
            return;
        }

        long minutesInPrep = Duration.between(
            item.getPreparationStartedAt(), 
            LocalDateTime.now()
        ).toMinutes();

        if (minutesInPrep > prepTime) {
            System.out.println("""
                [KITCHEN ALERT] Delayed item:
                Order ID: %d
                Table: %s
                Product: %s
                Expected time: %d minutes
                Current prep time: %d minutes
                """.formatted(
                    item.getOrderId(),
                    item.getOrderTableNumber(),
                    item.getProductName(),
                    prepTime,
                    minutesInPrep
                ));
        }
    }
}
