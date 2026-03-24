package com.dashapi.demo.infrastructure.persistence.jpa;

import com.dashapi.demo.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
