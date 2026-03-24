package com.dashapi.demo.domain.port.in;

import java.util.List;

import com.dashapi.demo.domain.model.Customer;

public interface CustomerUseCase {
    record CreateCustomerCommand(
            String name,
            Customer.PersonType personType,
            String document,
            String phone,
            String email
    ) {}

    List<Customer> listAll();
    Customer findById(Long id);
    Customer create(CreateCustomerCommand command);
    Customer update(Long id, CreateCustomerCommand command);
    void delete(Long id);
}
