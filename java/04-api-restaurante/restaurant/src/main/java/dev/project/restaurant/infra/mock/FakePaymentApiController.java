package dev.project.restaurant.infra.mock;

import dev.project.restaurant.application.dtos.PaymentRequest;
import dev.project.restaurant.application.dtos.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class FakePaymentApiController {

    @PostMapping("/payments/process")
    public ResponseEntity<PaymentResponse> mockProcessPayment(@RequestBody PaymentRequest request) {
        System.out.println("[FAKE API] Processing payment of: " + request.amount() + " via " + request.paymentMethod());
        
        PaymentResponse response = new PaymentResponse("APPROVED", UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }
}
