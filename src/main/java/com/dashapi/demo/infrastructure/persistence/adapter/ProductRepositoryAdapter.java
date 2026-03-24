package com.dashapi.demo.infrastructure.persistence.adapter;

import com.dashapi.demo.domain.model.Product;
import com.dashapi.demo.domain.port.out.ProductRepository;
import com.dashapi.demo.infrastructure.persistence.jpa.SpringProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringProductRepository spring;

    public ProductRepositoryAdapter(SpringProductRepository spring) {
        this.spring = spring;
    }

    @Override
    public List<Product> findAll() {
        return spring.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return spring.findById(id);
    }

    @Override
    public List<String> findAllCategories() {
        return spring.findAllCategories();
    }

    @Override
    public Product save(Product product) {
        return spring.save(product);
    }

    @Override
    public void deleteById(Long id) {
        spring.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return spring.existsById(id);
    }

    @Override
    public long count() {
        return spring.count();
    }
}
