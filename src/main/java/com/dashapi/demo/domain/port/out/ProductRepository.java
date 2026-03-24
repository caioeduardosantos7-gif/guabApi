package com.dashapi.demo.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.dashapi.demo.domain.model.Product;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    List<String> findAllCategories();
    Product save(Product product);
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();
}
