package dev.project.restaurant.api.docs;

import dev.project.restaurant.application.dtos.ProductRequest;
import dev.project.restaurant.application.dtos.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Products", description = "Management of restaurant menu products")
public interface ProductControllerDoc {

    @Operation(summary = "Register a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request payload data"),
        @ApiResponse(responseCode = "404", description = "Product category not found")
    })
    ProductResponse create(ProductRequest request);

    @Operation(summary = "List all products with pagination")
    @ApiResponse(responseCode = "200", description = "Paginated list of products retrieved successfully")
    Page<ProductResponse> listAll(Pageable pageable);

    @Operation(summary = "Find a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ProductResponse findById(Long id);

    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request payload data"),
        @ApiResponse(responseCode = "404", description = "Product or category not found")
    })
    ProductResponse update(Long id, ProductRequest request);

    @Operation(summary = "Delete a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "409", description = "Data integrity violation - Product linked to active orders")
    })
    void delete(Long id);
}
