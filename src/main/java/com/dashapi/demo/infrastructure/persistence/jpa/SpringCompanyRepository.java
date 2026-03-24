package com.dashapi.demo.infrastructure.persistence.jpa;

import com.dashapi.demo.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringCompanyRepository extends JpaRepository<Company, Long> {}
