package com.dashapi.demo.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashapi.demo.domain.model.Customer;
import com.dashapi.demo.domain.port.in.CustomerUseCase;
import com.dashapi.demo.domain.port.out.CustomerRepository;

@Service
@Transactional
public class CustomerService implements CustomerUseCase {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }

    @Override
    public Customer create(CreateCustomerCommand command) {
        Customer customer = new Customer(null, command.name(), command.personType(),
                command.document(), command.phone(), command.email(),
                LocalDateTime.now(), LocalDateTime.now());
        return repository.save(customer);
    }

    @Override
    public Customer update(Long id, CreateCustomerCommand command) {
        Customer existing = findById(id);
        Customer updated = new Customer(existing.getId(), command.name(), command.personType(),
                command.document(), command.phone(), command.email(),
                existing.getCreatedAt(), LocalDateTime.now());
        return repository.save(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Customer not found: " + id);
        }
        repository.deleteById(id);
    }
}
