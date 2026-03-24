package com.dashapi.demo.infrastructure.persistence.jpa;

import com.dashapi.demo.domain.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringSaleRepository extends JpaRepository<Sale, Long> {}
