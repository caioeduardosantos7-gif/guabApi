package com.dashapi.demo.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashapi.demo.domain.model.Transaction;
import com.dashapi.demo.domain.port.in.TransactionUseCase;
import com.dashapi.demo.domain.port.out.TransactionRepository;

@Service
@Transactional(readOnly = true)
public class TransactionService implements TransactionUseCase {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transaction> listAll() {
        return repository.findAll();
    }
}
