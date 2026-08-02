package dev.project.restaurant.api.docs;

import dev.project.restaurant.application.dtos.BillCloseRequest;
import dev.project.restaurant.application.dtos.BillCloseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bill Closures", description = "Management of restaurant bill closing and billing workflows")
public interface BillCloseControllerDoc {

    @Operation(summary = "Close a bill for a specific order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bill closed successfully"),
        @ApiResponse(responseCode = "400", description = "Order is already closed, cancelled, contains undelivered items, or negative financial values"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    BillCloseResponse closeBill(Long orderId, BillCloseRequest request);

    @Operation(summary = "Find bill closure details by order ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bill closure details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Bill closure not found for the given order ID")
    })
    BillCloseResponse findByOrderId(Long orderId);
}
