package com.dashapi.demo.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.dashapi.demo.domain.model.Sale;

public interface SaleRepository {
    List<Sale> findAll();
    Optional<Sale> findById(Long id);
    Sale save(Sale sale);
}
