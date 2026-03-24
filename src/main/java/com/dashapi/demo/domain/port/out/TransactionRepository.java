package com.dashapi.demo.domain.port.out;

import java.util.List;

import com.dashapi.demo.domain.model.Transaction;

public interface TransactionRepository {
    List<Transaction> findAll();
}
