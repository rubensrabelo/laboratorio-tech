package dev.project.restaurant.api.docs;

import dev.project.restaurant.application.dtos.OrderItemRequest;
import dev.project.restaurant.application.dtos.OrderItemResponse;
import dev.project.restaurant.application.dtos.OrderRequest;
import dev.project.restaurant.application.dtos.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "Orders", description = "Management of restaurant orders and items")
public interface OrderControllerDoc {

    @Operation(summary = "Open a new order for a table")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order opened successfully"),
        @ApiResponse(responseCode = "400", description = "Table is not available or invalid payload data"),
        @ApiResponse(responseCode = "404", description = "Table not found")
    })
    OrderResponse openOrder(OrderRequest request);

    @Operation(summary = "List all orders with pagination")
    @ApiResponse(responseCode = "200", description = "Paginated list of orders retrieved successfully")
    Page<OrderResponse> listAll(Pageable pageable);

    @Operation(summary = "Find an order by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    OrderResponse findById(Long id);

    @Operation(summary = "Add a new item to an open order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item added successfully"),
        @ApiResponse(responseCode = "400", description = "Order is not open, product is unavailable, or invalid quantity"),
        @ApiResponse(responseCode = "404", description = "Order or product not found")
    })
    OrderItemResponse addItem(Long orderId, OrderItemRequest request);

    @Operation(summary = "List all items from a specific order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of order items retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    List<OrderItemResponse> listItems(Long orderId);
}
