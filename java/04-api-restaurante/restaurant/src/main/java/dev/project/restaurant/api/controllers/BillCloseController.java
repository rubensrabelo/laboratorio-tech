package dev.project.restaurant.api.controllers;

import dev.project.restaurant.api.docs.BillCloseControllerDoc;
import dev.project.restaurant.application.dtos.BillCloseRequest;
import dev.project.restaurant.application.dtos.BillCloseResponse;
import dev.project.restaurant.application.services.BillCloseService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class BillCloseController implements BillCloseControllerDoc {

    private final BillCloseService billCloseService;

    public BillCloseController(BillCloseService billCloseService) {
        this.billCloseService = billCloseService;
    }

    @Override
    @PostMapping("/{id}/bill-closing")
    @ResponseStatus(HttpStatus.CREATED)
    public BillCloseResponse closeBill(
            @PathVariable("id") Long orderId,
            @Valid @RequestBody BillCloseRequest request
    ) {
        return billCloseService.closeBill(orderId, request);
    }

    @Override
    @GetMapping("/{id}/bill-closing")
    public BillCloseResponse findByOrderId(@PathVariable("id") Long orderId) {
        return billCloseService.findByOrderId(orderId);
    }
}
