package com.dashapi.demo.domain.port.in;

import java.math.BigDecimal;
import java.util.List;

import com.dashapi.demo.domain.model.Sale;

public interface SaleUseCase {
    record SaleItemCommand(String product, int quantity, BigDecimal price, String method) {}
    record CreateSaleCommand(Long customerId, List<SaleItemCommand> items) {}

    List<Sale> listAll();
    Sale create(CreateSaleCommand command);
}
