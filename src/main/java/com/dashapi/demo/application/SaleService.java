package com.dashapi.demo.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashapi.demo.domain.model.Customer;
import com.dashapi.demo.domain.model.Sale;
import com.dashapi.demo.domain.model.SaleItem;
import com.dashapi.demo.domain.port.in.SaleUseCase;
import com.dashapi.demo.domain.port.out.CustomerRepository;
import com.dashapi.demo.domain.port.out.SaleRepository;

@Service
@Transactional
public class SaleService implements SaleUseCase {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;

    public SaleService(SaleRepository saleRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sale> listAll() {
        return saleRepository.findAll();
    }

    @Override
    public Sale create(CreateSaleCommand command) {
        Customer customer = null;
        if (command.customerId() != null) {
            customer = customerRepository.findById(command.customerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + command.customerId()));
        }

        List<SaleItem> items = command.items().stream()
                .map(i -> new SaleItem(null, null, i.product(), i.quantity(), i.price(), i.method()))
                .toList();

        BigDecimal total = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Sale sale = new Sale(null, customer, total, items, LocalDateTime.now());
        return saleRepository.save(sale);
    }
}
