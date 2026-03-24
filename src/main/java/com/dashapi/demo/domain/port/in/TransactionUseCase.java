package com.dashapi.demo.domain.port.in;

import java.util.List;

import com.dashapi.demo.domain.model.Transaction;

public interface TransactionUseCase {
    List<Transaction> listAll();
}
