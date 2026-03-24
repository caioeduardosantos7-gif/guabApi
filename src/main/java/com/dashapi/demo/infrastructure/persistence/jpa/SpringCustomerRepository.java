package com.dashapi.demo.infrastructure.persistence.jpa;

import com.dashapi.demo.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringCustomerRepository extends JpaRepository<Customer, Long> {}
