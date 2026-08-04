package dev.project.restaurant.application.services;

import dev.project.restaurant.application.dtos.PaymentRequest;
import dev.project.restaurant.application.dtos.PaymentResponse;
import dev.project.restaurant.domain.BillClosing;
import dev.project.restaurant.domain.Payment;
import dev.project.restaurant.domain.RestaurantTable;
import dev.project.restaurant.domain.Order;
import dev.project.restaurant.domain.enums.PaymentMethod;
import dev.project.restaurant.domain.enums.TableStatus;
import dev.project.restaurant.exceptions.domain.BusinessException;
import dev.project.restaurant.domain.enums.PaymentStatus;
import dev.project.restaurant.domain.enums.OrderStatus;
import dev.project.restaurant.infra.client.PaymentClient;
import dev.project.restaurant.infra.repositories.BillClosingRepository;
import dev.project.restaurant.infra.repositories.PaymentRepository;
import dev.project.restaurant.infra.repositories.RestaurantTableRepository;
import dev.project.restaurant.infra.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentClient paymentClient;
    private final BillClosingRepository billClosingRepository;
    private final OrderRepository orderRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(
            PaymentClient paymentClient,
            BillClosingRepository billClosingRepository,
            OrderRepository orderRepository,
            RestaurantTableRepository restaurantTableRepository,
            PaymentRepository paymentRepository
    ) {
        this.paymentClient = paymentClient;
        this.billClosingRepository = billClosingRepository;
        this.orderRepository = orderRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processPayment(Long orderId, String paymentMethodStr) {

        BillClosing billClosing = billClosingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException("Bill not found."));

        PaymentResponse response = paymentClient.process(
                new PaymentRequest(
                        billClosing.getTotal(),
                        paymentMethodStr
                )
        );

        if ("APPROVED".equals(response.status())) {
            Order order = billClosing.getOrder();
            order.setStatus(OrderStatus.CLOSED);

            RestaurantTable table = order.getTable();
            table.setStatus(TableStatus.AVAILABLE);

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setPaymentMethod(PaymentMethod.fromString(paymentMethodStr));
            payment.setStatus(PaymentStatus.APPROVED);
            payment.setAmount(billClosing.getTotal());
            payment.setPaidAt(billClosing.getClosedAt());

            orderRepository.save(order);
            restaurantTableRepository.save(table);
            paymentRepository.save(payment);
        }
    }
}
