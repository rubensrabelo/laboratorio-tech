package dev.project.restaurant.infra.client;

import dev.project.restaurant.application.dtos.PaymentRequest;
import dev.project.restaurant.application.dtos.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-client", url = "${payment.api.url}")
public interface PaymentClient {

    @PostMapping("/payments/process")
    PaymentResponse process(@RequestBody PaymentRequest request);
}
