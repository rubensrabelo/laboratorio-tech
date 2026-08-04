package dev.project.restaurant.api.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payments", description = "Management of order payments and billing")
public interface PaymentControllerDoc {

    @Operation(summary = "Process the payment for an order and free the table")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment processed and approved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid payment method or business logic validation failed"),
        @ApiResponse(responseCode = "404", description = "Bill or order not found")
    })
    void processPayment(
        @Parameter(description = "ID of the order to pay") Long orderId,
        @Parameter(description = "Payment method (CASH, CREDIT_CARD, DEBIT_CARD, PIX)") String paymentMethod
    );
}
