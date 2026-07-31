package dev.project.restaurant.application.services;

import dev.project.restaurant.application.dtos.ProductRequest;
import dev.project.restaurant.application.dtos.ProductResponse;
import dev.project.restaurant.domain.Product;
import dev.project.restaurant.domain.ProductCategory;
import dev.project.restaurant.exceptions.domain.DataIntegrityViolationException;
import dev.project.restaurant.exceptions.domain.ResourceNotFoundException;
import dev.project.restaurant.infra.repositories.ProductCategoryRepository;
import dev.project.restaurant.infra.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(
            ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository
    ) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        ProductCategory category = findCategoryEntityById(request.categoryId());
        Product product = request.toEntity(category);
        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> listAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = findProductEntityById(id);
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findProductEntityById(id);
        ProductCategory category = findCategoryEntityById(request.categoryId());

        request.fill(product, category);
        Product updatedProduct = productRepository.save(product);

        return ProductResponse.fromEntity(updatedProduct);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        try {
            productRepository.deleteById(id);
            productRepository.flush(); // Força o Hibernate a checar as FKs imediatamente dentro do try-catch
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("This product cannot be deleted because it is linked to active order items.");
        }
    }

    private Product findProductEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    private ProductCategory findCategoryEntityById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product category not found with ID: " + id));
    }
}
