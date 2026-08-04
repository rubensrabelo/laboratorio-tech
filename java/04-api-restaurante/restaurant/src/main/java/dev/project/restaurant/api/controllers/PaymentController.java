package dev.project.restaurant.api.controllers;

import dev.project.restaurant.api.docs.PaymentControllerDoc;
import dev.project.restaurant.application.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController implements PaymentControllerDoc {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    @PostMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void processPayment(
            @PathVariable Long orderId,
            @RequestParam("paymentMethod") String paymentMethod
    ) {
        paymentService.processPayment(orderId, paymentMethod);
    }
}
