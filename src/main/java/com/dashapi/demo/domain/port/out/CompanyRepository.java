package com.dashapi.demo.domain.port.out;

import java.util.Optional;

import com.dashapi.demo.domain.model.Company;

public interface CompanyRepository {
    Optional<Company> findById(Long id);
    Company save(Company company);
}
