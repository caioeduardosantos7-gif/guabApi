package com.dashapi.demo.infrastructure.persistence.adapter;

import com.dashapi.demo.domain.model.Transaction;
import com.dashapi.demo.domain.port.out.TransactionRepository;
import com.dashapi.demo.infrastructure.persistence.jpa.SpringTransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final SpringTransactionRepository spring;

    public TransactionRepositoryAdapter(SpringTransactionRepository spring) {
        this.spring = spring;
    }

    @Override
    public List<Transaction> findAll() {
        return spring.findAll();
    }
}
