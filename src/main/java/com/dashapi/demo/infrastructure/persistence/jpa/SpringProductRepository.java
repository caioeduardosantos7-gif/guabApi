package com.dashapi.demo.infrastructure.persistence.jpa;

import com.dashapi.demo.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT p.category FROM Product p ORDER BY p.category")
    List<String> findAllCategories();
}
