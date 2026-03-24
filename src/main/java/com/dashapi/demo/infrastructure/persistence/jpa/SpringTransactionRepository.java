package com.dashapi.demo.infrastructure.persistence.jpa;

import com.dashapi.demo.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringTransactionRepository extends JpaRepository<Transaction, String> {}
