package com.dashapi.demo.infrastructure.persistence.adapter;

import com.dashapi.demo.domain.model.Sale;
import com.dashapi.demo.domain.port.out.SaleRepository;
import com.dashapi.demo.infrastructure.persistence.jpa.SpringSaleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SaleRepositoryAdapter implements SaleRepository {

    private final SpringSaleRepository spring;

    public SaleRepositoryAdapter(SpringSaleRepository spring) {
        this.spring = spring;
    }

    @Override
    public List<Sale> findAll() {
        return spring.findAll();
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return spring.findById(id);
    }

    @Override
    public Sale save(Sale sale) {
        return spring.save(sale);
    }
}
