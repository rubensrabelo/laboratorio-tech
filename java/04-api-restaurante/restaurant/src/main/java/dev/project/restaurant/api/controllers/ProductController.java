package dev.project.restaurant.api.controllers;

import dev.project.restaurant.api.docs.ProductControllerDoc;
import dev.project.restaurant.application.dtos.ProductRequest;
import dev.project.restaurant.application.dtos.ProductResponse;
import dev.project.restaurant.application.services.ProductService;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController implements ProductControllerDoc {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @Override
    @GetMapping
    public Page<ProductResponse> listAll(@ParameterObject Pageable pageable) {
        return productService.listAll(pageable);
    }

    @Override
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
