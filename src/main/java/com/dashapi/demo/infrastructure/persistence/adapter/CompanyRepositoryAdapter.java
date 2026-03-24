package com.dashapi.demo.infrastructure.persistence.adapter;

import com.dashapi.demo.domain.model.Company;
import com.dashapi.demo.domain.port.out.CompanyRepository;
import com.dashapi.demo.infrastructure.persistence.jpa.SpringCompanyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyRepositoryAdapter implements CompanyRepository {

    private final SpringCompanyRepository spring;

    public CompanyRepositoryAdapter(SpringCompanyRepository spring) {
        this.spring = spring;
    }

    @Override
    public Optional<Company> findById(Long id) {
        return spring.findById(id);
    }

    @Override
    public Company save(Company company) {
        return spring.save(company);
    }
}
