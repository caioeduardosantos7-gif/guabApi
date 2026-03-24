package com.dashapi.demo.adapter.http.product;

import com.dashapi.demo.domain.model.Product;
import com.dashapi.demo.domain.port.in.ProductUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductUseCase productUseCase;
    private final ProductMapper mapper;

    public ProductController(ProductUseCase productUseCase, ProductMapper mapper) {
        this.productUseCase = productUseCase;
        this.mapper = mapper;
    }

    record ProductRequest(
            @NotBlank String name,
            @NotBlank String category,
            @NotNull @Positive BigDecimal price,
            BigDecimal oldPrice,
            @NotBlank String description,
            @NotNull List<String> tags,
            @NotBlank String image) {}

    @GetMapping
    public List<Product> list() {
        return productUseCase.listAll();
    }

    // Declared BEFORE /{id} to avoid route conflict
    @GetMapping("/categories")
    public List<String> categories() {
        return productUseCase.listCategories();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productUseCase.findById(id);
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest req) {
        Product created = productUseCase.create(mapper.toCommand(req));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return productUseCase.update(id, mapper.toCommand(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
