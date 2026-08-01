package dev.project.restaurant.api.controllers;

import dev.project.restaurant.api.docs.OrderControllerDoc;
import dev.project.restaurant.application.dtos.OrderItemRequest;
import dev.project.restaurant.application.dtos.OrderItemResponse;
import dev.project.restaurant.application.dtos.OrderRequest;
import dev.project.restaurant.application.dtos.OrderResponse;
import dev.project.restaurant.application.services.OrderService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController implements OrderControllerDoc {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse openOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.openOrder(request);
    }

    @Override
    @GetMapping
    public Page<OrderResponse> listAll(@ParameterObject Pageable pageable) {
        return orderService.listAll(pageable);
    }

    @Override
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @Override
    @PostMapping("/{id}/items")
    public OrderItemResponse addItem(
            @PathVariable("id") Long orderId,
            @Valid @RequestBody OrderItemRequest request
    ) {
        return orderService.addItem(orderId, request);
    }

    @Override
    @GetMapping("/{id}/items")
    public List<OrderItemResponse> listItems(@PathVariable("id") Long orderId) {
        return orderService.listItems(orderId);
    }
}
