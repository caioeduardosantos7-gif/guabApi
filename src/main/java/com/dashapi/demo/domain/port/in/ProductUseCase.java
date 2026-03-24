package com.dashapi.demo.domain.port.in;

import java.math.BigDecimal;
import java.util.List;

import com.dashapi.demo.domain.model.Product;

public interface ProductUseCase {
    record SaveProductCommand(
            String name,
            String category,
            BigDecimal price,
            BigDecimal oldPrice,
            String description,
            List<String> tags,
            String image
    ) {}

    List<Product> listAll();
    List<String> listCategories();
    Product findById(Long id);
    Product create(SaveProductCommand command);
    Product update(Long id, SaveProductCommand command);
    void delete(Long id);
}
