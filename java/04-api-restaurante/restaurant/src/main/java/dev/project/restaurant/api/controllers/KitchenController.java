package dev.project.restaurant.api.controllers;

import dev.project.restaurant.api.docs.KitchenControllerDoc;
import dev.project.restaurant.application.dtos.KitchenItemResponse;
import dev.project.restaurant.application.services.KitchenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchen")
public class KitchenController implements KitchenControllerDoc {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @Override
    @GetMapping("/items/pending")
    public List<KitchenItemResponse> listPendingItems() {
        return kitchenService.listPendingItems();
    }

    @Override
    @GetMapping("/items/preparing")
    public List<KitchenItemResponse> listPreparingItems() {
        return kitchenService.listPreparingItems();
    }

    @Override
    @PatchMapping("/items/{itemId}/start-preparation")
    public KitchenItemResponse startPreparation(@PathVariable("itemId") Long itemId) {
        return kitchenService.startPreparation(itemId);
    }

    @Override
    @PatchMapping("/items/{itemId}/mark-ready")
    public KitchenItemResponse markAsReady(@PathVariable("itemId") Long itemId) {
        return kitchenService.markAsReady(itemId);
    }

    @Override
    @PatchMapping("/items/{itemId}/deliver")
    public KitchenItemResponse deliverItem(@PathVariable("itemId") Long itemId) {
        return kitchenService.deliverItem(itemId);
    }
}
