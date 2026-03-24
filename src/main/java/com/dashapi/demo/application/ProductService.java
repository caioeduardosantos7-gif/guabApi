package com.dashapi.demo.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashapi.demo.domain.model.Product;
import com.dashapi.demo.domain.port.in.ProductUseCase;
import com.dashapi.demo.domain.port.out.ProductRepository;

@Service
@Transactional
public class ProductService implements ProductUseCase {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> listAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> listCategories() {
        return repository.findAllCategories();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    @Override
    public Product create(SaveProductCommand command) {
        Product product = new Product(null, command.name(), command.category(),
                command.price(), command.oldPrice(), command.description(),
                command.tags(), command.image(), LocalDateTime.now(), LocalDateTime.now());
        return repository.save(product);
    }

    @Override
    public Product update(Long id, SaveProductCommand command) {
        Product existing = findById(id);
        Product updated = new Product(existing.getId(), command.name(), command.category(),
                command.price(), command.oldPrice(), command.description(),
                command.tags(), command.image(), existing.getCreatedAt(), LocalDateTime.now());
        return repository.save(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Product not found: " + id);
        }
        repository.deleteById(id);
    }
}
