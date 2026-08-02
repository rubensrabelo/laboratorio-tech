package dev.project.restaurant.api.docs;

import dev.project.restaurant.application.dtos.KitchenItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Kitchen", description = "Management of kitchen preparation steps and items")
public interface KitchenControllerDoc {

    @Operation(summary = "List all pending kitchen items")
    @ApiResponse(responseCode = "200", description = "List of pending items retrieved successfully")
    List<KitchenItemResponse> listPendingItems();

    @Operation(summary = "List all kitchen items currently in preparation")
    @ApiResponse(responseCode = "200", description = "List of preparing items retrieved successfully")
    List<KitchenItemResponse> listPreparingItems();

    @Operation(summary = "Start preparation for a pending item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Preparation started successfully"),
        @ApiResponse(responseCode = "400", description = "Item is not pending"),
        @ApiResponse(responseCode = "404", description = "Order item not found")
    })
    KitchenItemResponse startPreparation(Long itemId);

    @Operation(summary = "Mark an item in preparation as ready")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item marked as ready successfully"),
        @ApiResponse(responseCode = "400", description = "Item is not in preparation"),
        @ApiResponse(responseCode = "404", description = "Order item not found")
    })
    KitchenItemResponse markAsReady(Long itemId);

    @Operation(summary = "Deliver a ready kitchen item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item delivered successfully"),
        @ApiResponse(responseCode = "400", description = "Item is not ready"),
        @ApiResponse(responseCode = "404", description = "Order item not found")
    })
    KitchenItemResponse deliverItem(Long itemId);
}
