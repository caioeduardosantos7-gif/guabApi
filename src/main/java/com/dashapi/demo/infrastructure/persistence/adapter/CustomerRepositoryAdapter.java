package com.dashapi.demo.infrastructure.persistence.adapter;

import com.dashapi.demo.domain.model.Customer;
import com.dashapi.demo.domain.port.out.CustomerRepository;
import com.dashapi.demo.infrastructure.persistence.jpa.SpringCustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final SpringCustomerRepository spring;

    public CustomerRepositoryAdapter(SpringCustomerRepository spring) {
        this.spring = spring;
    }

    @Override
    public List<Customer> findAll() {
        return spring.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return spring.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return spring.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        spring.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return spring.existsById(id);
    }
}
