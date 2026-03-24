package com.dashapi.demo.adapter.http.transaction;

import com.dashapi.demo.domain.model.Transaction;
import com.dashapi.demo.domain.port.in.TransactionUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    public TransactionController(TransactionUseCase transactionUseCase) {
        this.transactionUseCase = transactionUseCase;
    }

    @GetMapping
    public List<Transaction> list() {
        return transactionUseCase.listAll();
    }
}
