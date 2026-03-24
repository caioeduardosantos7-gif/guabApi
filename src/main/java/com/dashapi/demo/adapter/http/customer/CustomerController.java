package com.dashapi.demo.adapter.http.customer;

import com.dashapi.demo.domain.model.Customer;
import com.dashapi.demo.domain.port.in.CustomerUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    public CustomerController(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    record CustomerRequest(
            @NotBlank String name,
            @NotNull Customer.PersonType personType,
            @NotBlank String document,
            @NotBlank String phone,
            @NotBlank String email) {}

    @GetMapping
    public List<Customer> list() {
        return customerUseCase.listAll();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return customerUseCase.findById(id);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerRequest req) {
        Customer created = customerUseCase.create(new CustomerUseCase.CreateCustomerCommand(
                req.name(), req.personType(), req.document(), req.phone(), req.email()));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @Valid @RequestBody CustomerRequest req) {
        return customerUseCase.update(id, new CustomerUseCase.CreateCustomerCommand(
                req.name(), req.personType(), req.document(), req.phone(), req.email()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
