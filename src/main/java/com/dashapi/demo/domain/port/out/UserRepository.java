package com.dashapi.demo.domain.port.out;

import java.util.Optional;

import com.dashapi.demo.domain.model.User;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User save(User user);
}
